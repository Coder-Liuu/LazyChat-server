package chatroom.handler;

import chatroom.ChatServer;
import chatroom.dao.UserFriendMapperService;
import chatroom.message.Constant;
import chatroom.message.NoticeResponseMessage;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ChannelHandler.Sharable
@Slf4j
public class QuitHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = SessionFactory.getSession();
        String username = session.getUsername(ctx.channel());
        log.error("{} 已经下线", username);
        List<String> friendsNotice = UserFriendMapperService.getFriends(username);
        log.error("{}", friendsNotice);
        // 用户下线通知
        for (String friend : friendsNotice) {
            Channel ch = session.getChannel(friend);
            if (ch == null) {
                continue;
            }
            NoticeResponseMessage msg = new NoticeResponseMessage(Constant.NOTICE_OFFLINE, username, friend, "");
            ch.writeAndFlush(msg);
        }
    }
}
