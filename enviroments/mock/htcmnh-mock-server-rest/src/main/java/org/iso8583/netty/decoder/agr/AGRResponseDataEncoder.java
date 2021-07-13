package org.iso8583.netty.decoder.agr;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MockISO8583Server;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;

@Slf4j
public class AGRResponseDataEncoder extends MessageToByteEncoder<ISOMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ISOMsg isoMsg, ByteBuf byteBuf) throws Exception {
        byte[] msg = pack(isoMsg);
        byteBuf.writeBytes(msg);
    }


    public byte[] pack(ISOMsg obj) {
        try {
            InputStream inputStream = MockISO8583Server.class.getResourceAsStream("/agridef.xml");
            ISOPackager packager = new GenericPackager(inputStream);
            obj.setPackager(packager);
            byte[] arrByteRequest = obj.pack();

            //Add length header
            int l = arrByteRequest.length;
            byte[] msg = new byte[2 + l];

            byte[] header = ISOUtil.hex2byte(Integer.toString(l, 16)); // chuyen decimal sang hex--> byte

            log.info("data length " + arrByteRequest.length);
            if (header.length > 1) { // check length xem dai ko , dai qua 2 byte thi set 2 byte dau
                msg[0] = header[0];
                msg[1] = header[1];
            } else {
                msg[1] = header[0];
            }

            System.arraycopy(arrByteRequest, 0, msg, 2, arrByteRequest.length);
            log.info("data length " + msg.length);
            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
