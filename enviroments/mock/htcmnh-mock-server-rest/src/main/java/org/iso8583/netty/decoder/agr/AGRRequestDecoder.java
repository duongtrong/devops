package org.iso8583.netty.decoder.agr;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MockISO8583Server;
import org.iso8583.netty.utils.Utils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class AGRRequestDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        try {
            byte[] lenbuf = new byte[2];
            while (in.readableBytes() > 2) {
                in.markReaderIndex();

                in.readBytes(lenbuf, 0, 2); // doc 2 byte dau

                int size = Utils.hex2decimal(ISOUtil.hexString(lenbuf)); // size = hexString
                if (size > in.readableBytes()) {
                    throw new Exception();
                }
                byte[] data = new byte[size];

                in.readBytes(data, 0, size);

                log.info(String.format("Message receipt length %d - Data: %s", data.length, new String(data)));
                list.add(unpack(data));
            }
        } catch (Exception ex) {
        }

    }


    public ISOMsg unpack(byte[] data) {
        try {
            InputStream inputStream = MockISO8583Server.class.getResourceAsStream("/agridef.xml");
            ISOPackager packager = new GenericPackager(inputStream);
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(data);
            return isoMsg;
        } catch (Exception ex) {
            log.error("Can not unpack message: ", ex);
            return null;
        }
    }
}
