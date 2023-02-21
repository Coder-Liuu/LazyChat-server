package chatroom;

import chatroom.message.LoginRequestMessage;
import chatroom.message.LoginResponseMessage;
import chatroom.protocol.MessageCodec;
import chatroom.service.UserServiceFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ChannelFuture server = new ServerBootstrap()
            .channel(NioServerSocketChannel.class)
            .group(boss, worker)
            .childHandler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler());
                        pipeline.addLast(new MessageCodec());
                        pipeline.addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
                                System.out.println("msg: " + msg);
                                LoginResponseMessage loginResponseMessage;
                                if(login) {
                                    loginResponseMessage = new LoginResponseMessage(true, "ok");
                                }else {
                                    loginResponseMessage = new LoginResponseMessage(false, "用户名或密码不正确");
                                }
                                ctx.channel().writeAndFlush(loginResponseMessage);
                            }
                        });
                    }
                }
            ).bind(8080).sync();
    }
}
