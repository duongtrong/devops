package org.iso8583.netty.decoder;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.iso8583.MapErrorCode;
import org.iso8583.MockISO8583Server;
import org.jpos.iso.ISOMsg;

@Slf4j
public class ProcessingHandler extends SimpleChannelInboundHandler {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    log.info("chanel read");
    ChannelFuture future = null;
    try {
      if (o instanceof ISOMsg) {
        ISOMsg msg = (ISOMsg) o;
        System.out.println("ISO8583 Message received...");
        MockISO8583Server.logISOMsg(msg);
        msg.setMTI("410");
        MapErrorCode mapErrorCode = new MapErrorCode();
        ISOMsg response = mapErrorCode.map(msg);
        System.out.println("ISO8583 Message response...");
        MockISO8583Server.logISOMsg(response);
        future = channelHandlerContext.writeAndFlush(response);

        //future.addListener(ChannelFutureListener.CLOSE);
      }
    } catch (Exception e) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }
}
