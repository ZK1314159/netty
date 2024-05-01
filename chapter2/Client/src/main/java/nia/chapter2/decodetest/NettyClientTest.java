package nia.chapter2.decodetest;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Description
 *
 * @author zengkai
 * Date: 2021/12/29 14:05
 */
public class NettyClientTest {

  public static void main(String[] args) {

    EventLoopGroup workGroup = new NioEventLoopGroup();
    String inetAddress = "127.0.0.1";
    int port = 30002;
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(workGroup)
        .remoteAddress(new InetSocketAddress(inetAddress, port))
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          public void initChannel(SocketChannel socketChannel) {
            socketChannel.pipeline().addLast(new ClientMessageHandler());
          }
        });
      ChannelFuture channelFuture = bootstrap.connect();
      channelFuture.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          System.out.println("client start");
        }
      });
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
    }
  }

}
