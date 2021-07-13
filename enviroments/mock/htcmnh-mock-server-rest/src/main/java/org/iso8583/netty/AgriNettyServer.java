package org.iso8583.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.iso8583.netty.decoder.agr.AGRProcessingHandler;
import org.iso8583.netty.decoder.agr.AGRRequestDecoder;
import org.iso8583.netty.decoder.agr.AGRResponseDataEncoder;

import java.util.concurrent.TimeUnit;

public class AgriNettyServer implements Runnable {
    private int port;

    public AgriNettyServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("AgriBank server started at port..." + port);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new AGRRequestDecoder());
                            ch.pipeline().addFirst(new IdleStateHandler(0L, 0L, 10L, TimeUnit.SECONDS));
                            ch.pipeline().addLast(
                                    new AGRResponseDataEncoder(),
                                    new AGRProcessingHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
