package chatroom.service;

import chatroom.dao.UserFriendMapperService;
import chatroom.dao.UserMapperService;
import chatroom.message.NoticeResponseMessage;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;


public class NoticeServiceImpl implements NoticeService {
    @Override
    public void NoticeAddUser(String from_user, String to_user, int notice_type) {
        Session session = SessionFactory.getSession();
        if(notice_type == 1) {
            if(UserMapperService.getIdByUsername(to_user) == -1) {
                // 用户不存在
                return ;
            }
            // 如果在线直接转发
            Channel ch = session.getChannel(to_user);
            // 数据库操作
            UserFriendMapperService.insertStatus(from_user, to_user, "pending");
            if (ch != null) {
                // Socket操作
                NoticeResponseMessage msg = new NoticeResponseMessage(1, from_user, to_user, "");
                ch.writeAndFlush(msg);
            }
        }else if(notice_type == 2) {
            // 数据库操作
            UserFriendMapperService.updatePending(from_user, to_user);
            UserFriendMapperService.insertStatus(to_user, from_user, "friend");
            // Socket操作 两方都确认了，服务器给两方都发送添加成功的请求
            Channel ch_to_user = session.getChannel(to_user);
            Channel ch_from_user = session.getChannel(from_user);
            NoticeResponseMessage msg1 = new NoticeResponseMessage(2, from_user, to_user, "");
            ch_to_user.writeAndFlush(msg1);

            NoticeResponseMessage msg2 = new NoticeResponseMessage(2, to_user, from_user, "");
            ch_from_user.writeAndFlush(msg2);
        }
    }
}
