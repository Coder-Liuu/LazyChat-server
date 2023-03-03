package chatroom.service;

import chatroom.message.ChatAllResponseMessage;
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
}
