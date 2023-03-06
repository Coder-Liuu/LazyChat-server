package chatroom.handler;

import chatroom.dao.UserFriendMapperService;
import chatroom.message.LoginRequestMessage;
import chatroom.message.LoginResponseMessage;
import chatroom.message.NoticeResponseMessage;
import chatroom.service.UserServiceFactory;
import chatroom.session.SessionFactory;
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
        // 把所有好友信息发给客户端
        List<String> friends = UserFriendMapperService.getFriends(msg.getUsername());
        NoticeResponseMessage msg_friends = new NoticeResponseMessage(3, "system", msg.getUsername(), friends.toString());
        ctx.channel().writeAndFlush(msg_friends);
        // 把加好友的信息也转发给客户端
        List<String> pending_friends = UserFriendMapperService.getFriendsPending(msg.getUsername());
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println(pending_friends);
        for (String pending_friend : pending_friends) {
            NoticeResponseMessage msg_pending = new NoticeResponseMessage(1, pending_friend, msg.getUsername(), "");
            ctx.channel().writeAndFlush(msg_pending);
        }
    }
}
