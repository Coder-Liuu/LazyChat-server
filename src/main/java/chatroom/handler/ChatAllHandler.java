package chatroom.handler;

import chatroom.message.ChatAllRequestMessage;
import chatroom.service.ChatServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatAllHandler extends SimpleChannelInboundHandler<ChatAllRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatAllRequestMessage msg) throws Exception {
        ChatServiceFactory.getChatService().ChatAll(msg.getContent(), msg.getUsername());
    }
}
