package org.iso8583.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MockISO8583Server;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class RequestDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        try {
            while (in.readableBytes() > 6) {
                in.markReaderIndex();
                // bo qua 2 byte dau tien
                in.readByte();
                in.readByte();

                int thousand = in.readByte() - 48;
                int hundred = in.readByte() - 48;
                int ten = in.readByte() - 48;
                int unit = in.readByte() - 48;
                int size = thousand * 1000 + hundred * 100 + ten * 10 + unit;

                if (in.readableBytes() < size + 2) {
                    in.resetReaderIndex();
                    return;
                }
                ByteBuf bb = in.readSlice(size + 2);
                byte[] data = new byte[size + 2];
                bb.readBytes(data);
                byte[] result = new byte[size];
                System.arraycopy(data, 0, result, 0, size);
                log.info(String.format("Message receipt length %d - Data: %s", data.length, new String(data)));
                list.add(unpack(data));
            }
        } catch (Exception ex) {
            log.error("Error when decode Netty: ", ex);
        }
    }


    public ISOMsg unpack(byte[] data) {
        try {
            InputStream inputStream = MockISO8583Server.class.getResourceAsStream("/bankplusdef.xml");
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
