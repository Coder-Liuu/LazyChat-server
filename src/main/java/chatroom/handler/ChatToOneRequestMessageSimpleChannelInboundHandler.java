package chatroom.handler;

import chatroom.message.ChatToOneRequestMessage;
import chatroom.service.ChatServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatToOneRequestMessageSimpleChannelInboundHandler extends SimpleChannelInboundHandler<ChatToOneRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatToOneRequestMessage msg) throws Exception {
        System.out.println(msg);
        ChatServiceFactory.getChatService().ChatToOne(msg.getFrom_user(), msg.getTo_user(),
            msg.getContent());
    }
}
