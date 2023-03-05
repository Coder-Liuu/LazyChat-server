package chatroom;

import chatroom.handler.ChatAllRequestMessageSimpleChannelInboundHandler;
import chatroom.handler.ChatToOneRequestMessageSimpleChannelInboundHandler;
import chatroom.handler.LoginRequestMessageSimpleChannelInboundHandler;
import chatroom.handler.NoticeRequestMessageSimpleChannelInboundHandler;
import chatroom.protocol.MessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    public static final LoggingHandler LOGGING_HANDLER = new LoggingHandler();
    public static final LoginRequestMessageSimpleChannelInboundHandler LOGIN_REQUEST_HANDLER = new LoginRequestMessageSimpleChannelInboundHandler();
    public static final ChatAllRequestMessageSimpleChannelInboundHandler CHATALL_REQUEST_HANDLER = new ChatAllRequestMessageSimpleChannelInboundHandler();
    public static final ChatToOneRequestMessageSimpleChannelInboundHandler CHAT_TO_ONE_REQUEST_HANDLER = new ChatToOneRequestMessageSimpleChannelInboundHandler();
    public static final NoticeRequestMessageSimpleChannelInboundHandler NOTICE_REQUEST_HANDLER = new NoticeRequestMessageSimpleChannelInboundHandler();

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
                        pipeline.addLast(LOGGING_HANDLER);
                        pipeline.addLast(new MessageCodec());
                        // 处理登陆业务
                        pipeline.addLast(LOGIN_REQUEST_HANDLER);
                        // 处理全部聊天的业务
                        pipeline.addLast(CHATALL_REQUEST_HANDLER);
                        // 处理私聊的业务
                        pipeline.addLast(CHAT_TO_ONE_REQUEST_HANDLER);
                        // 处理通知类的业务
                        pipeline.addLast(NOTICE_REQUEST_HANDLER);
                    }
                }
            ).bind(8080).sync();
    }

}
