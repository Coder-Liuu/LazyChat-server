package chatroom;

import chatroom.handler.ChatAllHandler;
import chatroom.handler.ChatToOneHandler;
import chatroom.handler.LoginHandler;
import chatroom.handler.NoticeHandler;
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
    public static final LoginHandler LOGIN_REQUEST_HANDLER = new LoginHandler();
    public static final ChatAllHandler CHATALL_REQUEST_HANDLER = new ChatAllHandler();
    public static final ChatToOneHandler CHAT_TO_ONE_REQUEST_HANDLER = new ChatToOneHandler();
    public static final NoticeHandler NOTICE_REQUEST_HANDLER = new NoticeHandler();

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
