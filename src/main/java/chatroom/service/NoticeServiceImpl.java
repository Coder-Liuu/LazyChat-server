package chatroom.service;

import chatroom.dao.UserDao;
import chatroom.db.ConnectionFactor;
import chatroom.message.NoticeResponseMessage;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
import com.alibaba.druid.util.JdbcUtils;
import io.netty.channel.Channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class NoticeServiceImpl implements NoticeService {
    @Override
    public void NoticeAddUser(String from_user, String to_user, int notice_type) {
        Session session = SessionFactory.getSession();
        if(notice_type == 1) {
            if(UserDao.getByUsername(to_user) == -1) {
                // 用户不存在
                return ;
            }
            Channel ch = session.getChannel(to_user);
            if (ch != null) {
                // 数据库操作
                UserDao.insertStatus(from_user, to_user, "pending");
                // Socket操作
                NoticeResponseMessage msg = new NoticeResponseMessage(1, from_user, to_user, "");
                ch.writeAndFlush(msg);
            }
        }else if(notice_type == 2) {
            // 数据库操作
            UserDao.updatePending(from_user, to_user);
            UserDao.insertStatus(to_user, from_user, "friend");
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
