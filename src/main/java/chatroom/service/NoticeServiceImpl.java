package chatroom.service;

import chatroom.dao.UserFriendMapperService;
import chatroom.dao.UserMapperService;
import chatroom.message.Constant;
import chatroom.message.NoticeResponseMessage;
import chatroom.session.Session;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NoticeServiceImpl implements NoticeService {
    /**
     * 张三请求添加李四为好友
     * @param from_user 张三
     * @param to_user 李四
     */
    @Override
    public void NoticeAddUserPending(String from_user, String to_user) {
        Session session = SessionFactory.getSession();
        if(UserMapperService.getIdByUsername(to_user) == -1) {
            Channel ch = session.getChannel(from_user);
            NoticeResponseMessage msg = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REFUSE, to_user, from_user, "。因为用户不存在");
            ch.writeAndFlush(msg);
            // 用户不存在
            return ;
        }
        // 如果在线直接转发
        Channel ch = session.getChannel(to_user);
        // 数据库操作
        UserFriendMapperService.insertStatus(from_user, to_user, "pending");
        if (ch != null) {
            // Socket操作
            NoticeResponseMessage msg = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REQ, from_user, to_user, "");
            ch.writeAndFlush(msg);
        }
    }

    /**
     * 李四通过了张三的请求
     * 因为客户端回复形式为(from_user=张三, to_user=李四)
     * 同一会话保持不变
     * @param from_user 张三
     * @param to_user 李四
     */
    @Override
    public void NoticeAddUserFriend(String from_user, String to_user) {
        Session session = SessionFactory.getSession();
        // 数据库操作
        UserFriendMapperService.updatePending(from_user, to_user, "friend");
        UserFriendMapperService.insertStatus(to_user, from_user, "friend");
        // Socket操作 两方都确认了，服务器给两方都发送添加成功的请求
        // 给李四发同意请求
        Channel ch_to_user = session.getChannel(to_user);
        NoticeResponseMessage msg1 = new NoticeResponseMessage(Constant.NOTICE_FRIEND_AGREE, from_user, to_user, "");
        ch_to_user.writeAndFlush(msg1);

        // 给张三发同意请求
        Channel ch_from_user = session.getChannel(from_user);
        log.warn("{}", ch_from_user);
        if(ch_from_user == null) {
            log.warn("Channel NULL");
            UserFriendMapperService.updateNotice(from_user, to_user, 1);
        }else {
            NoticeResponseMessage msg2 = new NoticeResponseMessage(Constant.NOTICE_FRIEND_AGREE, to_user, from_user, "");
            ch_from_user.writeAndFlush(msg2);
        }
    }

    /**
     * @param from_user 张三
     * @param to_user 李四
     */
    @Override
    public void NoticeAddUserRefuse(String from_user, String to_user) {
        Session session = SessionFactory.getSession();
        NoticeResponseMessage msg = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REFUSE, to_user, from_user, "");
        Channel channel = session.getChannel(from_user);
        if(channel == null) {
            UserFriendMapperService.updateNotice(from_user, to_user, 1);
            UserFriendMapperService.updatePending(from_user, to_user, "refuse");
        }else {
            channel.writeAndFlush(msg);
        }
    }

    /**
     * 删除用户
     * @param from_user
     * @param to_user
     */
    @Override
    public void NoticeRemoveUser(String from_user, String to_user) {
        Session session = SessionFactory.getSession();
        if(UserMapperService.getIdByUsername(to_user) == -1) {
            Channel ch = session.getChannel(from_user);
            NoticeResponseMessage msg = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REMOVE, to_user, from_user, "删除失败，因为用户不存在");
            ch.writeAndFlush(msg);
            // 用户不存在
            return ;
        }
        UserFriendMapperService.removeFriends(from_user, to_user);
        NoticeResponseMessage msg_from = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REMOVE, to_user, from_user, "。删除成功");
        session.getChannel(from_user).writeAndFlush(msg_from);
        NoticeResponseMessage msg_to = new NoticeResponseMessage(Constant.NOTICE_FRIEND_REMOVE, from_user, to_user, "。偷偷把你删除了");
        session.getChannel(to_user).writeAndFlush(msg_to);
    }
}
