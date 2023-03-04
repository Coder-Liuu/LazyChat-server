package chatroom.handler;

import chatroom.message.LoginRequestMessage;
import chatroom.message.LoginResponseMessage;
import chatroom.service.UserServiceFactory;
import chatroom.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestMessageSimpleChannelInboundHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        LoginResponseMessage loginResponseMessage;
        if (login) {
            loginResponseMessage = new LoginResponseMessage(true, "欢迎你:" + msg.getUsername());
            SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
        } else {
            loginResponseMessage = new LoginResponseMessage(false, "用户名或密码不正确");
        }
        System.out.println("loginResponseMessage --------------> " + loginResponseMessage.getMessageType());
        ctx.channel().writeAndFlush(loginResponseMessage);
    }
}
