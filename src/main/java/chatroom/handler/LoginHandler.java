package chatroom.handler;

import chatroom.dao.UserFriendMapperService;
import chatroom.message.Constant;
import chatroom.message.LoginRequestMessage;
import chatroom.message.LoginResponseMessage;
import chatroom.message.NoticeResponseMessage;
import chatroom.service.UserServiceFactory;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        LoginResponseMessage loginResponseMessage;
        // 登陆失败
        if (!login) {
            loginResponseMessage = new LoginResponseMessage(false, "密码不正确");
            ctx.channel().writeAndFlush(loginResponseMessage);
            return;
        }
        // 登陆成功
        loginResponseMessage = new LoginResponseMessage(true, "欢迎你:" + msg.getUsername());
        SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
        ctx.channel().writeAndFlush(loginResponseMessage);
        // 把好友添加成功给他发过去
        List<String> friendsNotice = UserFriendMapperService.getFriendsNotice(msg.getUsername());
        for (String notice : friendsNotice) {
            NoticeResponseMessage msg_notice = new NoticeResponseMessage(Constant.NOTICE_FRIEND_AGREE, notice, msg.getUsername(), "not append");
            UserFriendMapperService.updateNotice(msg.getUsername(), notice, 0);
            ctx.channel().writeAndFlush(msg_notice);
        }
        // 把加好友的信息也转发给客户端
        List<String> pending_friends = UserFriendMapperService.getFriendsPending(msg.getUsername());
        for (String pending_friend : pending_friends) {
            NoticeResponseMessage msg_pending = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REQ, pending_friend, msg.getUsername(), "");
            ctx.channel().writeAndFlush(msg_pending);
        }
        // 把所有好友信息发给客户端
        List<String> friends = UserFriendMapperService.getFriends(msg.getUsername());
        NoticeResponseMessage msg_friends = new NoticeResponseMessage(Constant.NOTICE_FRIEND_LIST, "system", msg.getUsername(), friends.toString());
        ctx.channel().writeAndFlush(msg_friends);
        // 给全部好友发送上线通知
        for (String friend : friends) {
            Channel channel = SessionFactory.getSession().getChannel(friend);
            // 如果好友上线
            if(channel != null) {
                NoticeResponseMessage online_msg = new NoticeResponseMessage(Constant.NOTICE_ONLINE, msg.getUsername(), friend, "");
                channel.writeAndFlush(online_msg);
            }
        }
    }
}
