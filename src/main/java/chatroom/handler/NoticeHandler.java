package chatroom.handler;

import chatroom.message.Constant;
import chatroom.message.NoticeRequestMessage;
import chatroom.service.NoticeService;
import chatroom.service.NoticeServiceFactor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class NoticeHandler extends SimpleChannelInboundHandler<NoticeRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NoticeRequestMessage msg) throws Exception {
        NoticeService noticeService = NoticeServiceFactor.getNoticeService();
        if (msg.getNotice_type() == Constant.NOTICE_FRIEND_REQ) {
            noticeService.NoticeAddUserPending(msg.getFrom_user(), msg.getTo_user());
        } else if (msg.getNotice_type() == Constant.NOTICE_FRIEND_AGREE) {
            noticeService.NoticeAddUserFriend(msg.getFrom_user(), msg.getTo_user());
        } else if (msg.getNotice_type() == Constant.NOTICE_FRIEND_REFUSE) {
            noticeService.NoticeAddUserRefuse(msg.getFrom_user(), msg.getTo_user());
        } else if (msg.getNotice_type() == Constant.NOTICE_FRIEND_REMOVE) {
            noticeService.NoticeRemoveUser(msg.getFrom_user(), msg.getTo_user());
        }
    }
}
