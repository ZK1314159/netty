package nia.chapter2.decodetest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

/**
 * Description
 *
 * @author zengkai
 * Date: 2021/12/29 11:40
 */
public class NettyServerTest {

  public static void main(String[] args) {
    final int port = 30002;
    EventLoopGroup boosGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    String delimiterString = "$$";
    byte[] delimiterBytes = delimiterString.getBytes();
    final ByteBuf delimiter = Unpooled.copiedBuffer(delimiterBytes);
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(boosGroup, workGroup)
        .channel(NioServerSocketChannel.class)
        .localAddress(new InetSocketAddress(port))
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          public void initChannel(SocketChannel channel) {
            channel.pipeline()
              .addLast(new DelimiterBasedFrameDecoder(1000, delimiter))
              .addLast(new StringDecoder())
              .addLast(new MessageHandler());
          }
        });
      ChannelFuture channelFuture = serverBootstrap.bind();
      channelFuture.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          System.out.println("server start");
        }
      });
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      boosGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

}
