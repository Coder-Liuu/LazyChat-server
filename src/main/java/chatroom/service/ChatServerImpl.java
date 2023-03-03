package chatroom.service;

import chatroom.message.ChatAllResponseMessage;
import chatroom.message.ChatToOneResponseMessage;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;

import java.util.Set;

public class ChatServerImpl implements ChatService {
    @Override
    public void ChatAll(String content, String username) {
        ChatAllResponseMessage resp = new ChatAllResponseMessage(content, username);
        Set<Channel> channels = SessionFactory.getSession().getChannels();
        for (Channel ch: channels) {
            ch.writeAndFlush(resp);
        }
    }

    @Override
    public void ChatToOne(String from_user, String to_user, String content) {
        ChatToOneResponseMessage resp = new ChatToOneResponseMessage(from_user, to_user, content);
        System.out.println("---> " + resp);
        Channel channel = SessionFactory.getSession().getChannel(to_user);
        channel.writeAndFlush(resp);
    }
}
