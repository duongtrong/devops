package org.iso8583.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MockISO8583Server;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

import java.io.InputStream;

@Slf4j
public class ResponseDataEncoder extends MessageToByteEncoder<ISOMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ISOMsg isoMsg, ByteBuf byteBuf) throws Exception {
        byte[] msg = pack(isoMsg);
        byteBuf.writeBytes(msg);
    }


    public byte[] pack(ISOMsg obj) {
        try {
            InputStream inputStream = MockISO8583Server.class.getResourceAsStream("/bankplusdef.xml");
            ISOPackager packager = new GenericPackager(inputStream);
            obj.setPackager(packager);
            byte[] arrByteRequest = obj.pack();

            int l = arrByteRequest.length;
            byte[] msg = new byte[4 + l];
            msg[0] = ((byte) (l / 1000 + 48));
            msg[1] = ((byte) (l % 1000 / 100 + 48));
            msg[2] = ((byte) (l % 100 / 10 + 48));
            msg[3] = ((byte) (l % 10 + 48));
            System.arraycopy(arrByteRequest, 0, msg, 4, arrByteRequest.length);

            byte[] msgBytes = msg;
            byte[] result = new byte[msgBytes.length + 4];
            System.arraycopy(Character.toString('\001').getBytes(), 0, result, 0, 1);
            System.arraycopy(":".getBytes(), 0, result, 1, 1);
            System.arraycopy(msgBytes, 0, result, 2, msgBytes.length);
            System.arraycopy(Character.toString('\001').getBytes(), 0, result, msgBytes.length + 2, 1);
            System.arraycopy("#".getBytes(), 0, result, msgBytes.length + 3, 1);
            return result;
//            return msgUtil.pack(obj);
        } catch (Exception ex) {
            log.error("Can not pack message: ", ex);
            return null;
        }
    }
}
