package chatroom.service;

import chatroom.message.NoticeResponseMessage;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;

public class NoticeServiceImpl implements NoticeService {
    @Override
    public void NoticeAddUser(String from_user, String to_user, int notice_type) {
        Session session = SessionFactory.getSession();
        if(notice_type == 1) {
            Channel ch = session.getChannel(to_user);
            if (ch != null) {
                System.out.println("消息转发成功");
                NoticeResponseMessage msg = new NoticeResponseMessage(1, from_user, to_user, "");
                ch.writeAndFlush(msg);
            } else {
                System.out.println("添加失败，用户不在线");
            }
        }else if(notice_type == 2) {
            // 两方都确认了，服务器给两方都发送添加成功的请求
            Channel ch_to_user = session.getChannel(to_user);
            Channel ch_from_user = session.getChannel(from_user);
            NoticeResponseMessage msg1 = new NoticeResponseMessage(2, from_user, to_user, "");
            ch_to_user.writeAndFlush(msg1);

            NoticeResponseMessage msg2 = new NoticeResponseMessage(2, to_user, from_user, "");
            ch_from_user.writeAndFlush(msg2);
        }
    }
}
