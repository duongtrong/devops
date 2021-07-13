package org.iso8583.netty.decoder.agr;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MapErrorCode;
import org.iso8583.MockISO8583Server;
import org.jpos.iso.ISOMsg;

@Slf4j
public class AGRProcessingHandler extends SimpleChannelInboundHandler {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    log.info("chanel read");
    ChannelFuture future = null;
    try {
      if (o instanceof ISOMsg) {
        ISOMsg msg = (ISOMsg) o;
        System.out.println("ISO8583 Message received...");
        MockISO8583Server.logISOMsg(msg);
        MapErrorCode mapErrorCode = new MapErrorCode();
        ISOMsg response = mapErrorCode.map(msg);
        System.out.println("ISO8583 Message response...");
        response.setMTI("210");
        MockISO8583Server.logISOMsg(response);
        future = channelHandlerContext.writeAndFlush(response);
        //future.addListener(ChannelFutureListener.CLOSE);
      }
    } catch (Exception e) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }
}
