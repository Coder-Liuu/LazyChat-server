package chatroom;

import chatroom.message.ChatAllRequestMessage;
import chatroom.message.ChatToOneRequestMessage;
import chatroom.message.LoginRequestMessage;
import chatroom.message.LoginResponseMessage;
import chatroom.protocol.MessageCodec;
import chatroom.service.ChatService;
import chatroom.service.ChatServiceFactory;
import chatroom.service.UserServiceFactory;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
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
                                LoginResponseMessage loginResponseMessage;
                                if(login) {
                                    loginResponseMessage = new LoginResponseMessage(true, "欢迎你:" + msg.getUsername());
                                    SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
                                }else {
                                    loginResponseMessage = new LoginResponseMessage(false, "用户名或密码不正确");
                                }
                                System.out.println("loginResponseMessage --------------> " + loginResponseMessage.getMessageType());
                                ctx.channel().writeAndFlush(loginResponseMessage);
                            }
                        });
                        // 处理全部聊天的业务
                        pipeline.addLast(new SimpleChannelInboundHandler<ChatAllRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatAllRequestMessage msg) throws Exception {
                                System.out.println(msg);
                                ChatServiceFactory.getChatService().ChatAll(msg.getContent(), msg.getUsername());
                            }
                        });
                        // 处理私聊的业务
                        pipeline.addLast(new SimpleChannelInboundHandler<ChatToOneRequestMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatToOneRequestMessage msg) throws Exception {
                                System.out.println(msg);
                                ChatServiceFactory.getChatService().ChatToOne(msg.getFrom_user(), msg.getTo_user(),
                                    msg.getContent());
                            }
                        });
                    }
                }
            ).bind(8080).sync();
    }
}
