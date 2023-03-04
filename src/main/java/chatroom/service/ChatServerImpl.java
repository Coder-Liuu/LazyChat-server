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
        for (Channel ch : channels) {
            ch.writeAndFlush(resp);
        }
    }

    @Override
    public void ChatToOne(String from_user, String to_user, String content) {
        Channel channel = SessionFactory.getSession().getChannel(to_user);

        if (channel == null) {
            Channel myChannel = SessionFactory.getSession().getChannel(from_user);
            ChatToOneResponseMessage resp = new ChatToOneResponseMessage(to_user, from_user, "(自动回复) 我不在线\n");
            myChannel.writeAndFlush(resp);
        } else {
            ChatToOneResponseMessage resp = new ChatToOneResponseMessage(from_user, to_user, content);
            channel.writeAndFlush(resp);
        }
    }
}
