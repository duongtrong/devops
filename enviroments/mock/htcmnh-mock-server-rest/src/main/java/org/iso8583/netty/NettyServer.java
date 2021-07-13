package org.iso8583.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.iso8583.netty.decoder.ProcessingHandler;
import org.iso8583.netty.decoder.RequestDecoder;
import org.iso8583.netty.decoder.ResponseDataEncoder;

import java.util.concurrent.TimeUnit;

public class NettyServer implements Runnable {

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    // constructor

    public static void main(String[] args) throws Exception {


        new Thread(new AgriNettyServer(6000)).start();

        new Thread(new NettyServer(5000)).start();


    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("MBBank server started at port..." + port);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new RequestDecoder());
                            ch.pipeline().addFirst(new IdleStateHandler(0L, 0L, 10L, TimeUnit.SECONDS));
                            ch.pipeline().addLast(
                                    new ResponseDataEncoder(),
                                    new ProcessingHandler());
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
