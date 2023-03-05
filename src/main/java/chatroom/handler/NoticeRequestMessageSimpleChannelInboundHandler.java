package chatroom.handler;

import chatroom.message.NoticeRequestMessage;
import chatroom.service.NoticeServiceFactor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class NoticeRequestMessageSimpleChannelInboundHandler extends SimpleChannelInboundHandler<NoticeRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NoticeRequestMessage msg) throws Exception {
        NoticeServiceFactor.getNoticeService().NoticeAddUser(msg.getFrom_user(), msg.getTo_user(), msg.getNotice_type());
    }
}
