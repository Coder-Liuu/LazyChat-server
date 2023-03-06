package chatroom.handler;

import chatroom.message.ChatToOneRequestMessage;
import chatroom.service.ChatServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatToOneHandler extends SimpleChannelInboundHandler<ChatToOneRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatToOneRequestMessage msg) throws Exception {
        ChatServiceFactory.getChatService().ChatToOne(msg.getFrom_user(), msg.getTo_user(),
            msg.getContent());
    }
}
