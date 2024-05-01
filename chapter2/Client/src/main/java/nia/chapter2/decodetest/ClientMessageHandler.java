package nia.chapter2.decodetest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Description
 *
 * @author zengkai
 * Date: 2021/12/29 14:24
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    String message = "tes天气话还得靠t mes的房价的浪费没看了sage$$fjdjflsdjfk$$";
    send(ctx, message);
    String message2 = "test messagefsd$$";
    send(ctx, message2);
    String message3 = "test message$$";
    send(ctx, message3);
    String message4 = "test message$$";
    send(ctx, message4);
  }

  private void send(ChannelHandlerContext ctx, String msg) {
    ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
    ctx.writeAndFlush(byteBuf);
  }

}
