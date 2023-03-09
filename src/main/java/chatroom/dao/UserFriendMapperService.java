package chatroom.dao;

import chatroom.dao.entity.UserFriend;
import chatroom.dao.mapper.DBSqlSessionFactor;
import chatroom.dao.mapper.UserFriendMapper;
import chatroom.session.SessionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class UserFriendMapperService {


    public static void insertStatus(String from_user, String to_user, String status) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);

            int user_id = UserMapperService.getIdByUsername(from_user);
            int friend_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.insertStatus(user_id, friend_id, status);
            session.commit();
        }
    }

    public static void updatePending(String from_user, String to_user, String status) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);

            int user_id = UserMapperService.getIdByUsername(from_user);
            int friend_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.updatePending(user_id, friend_id, status);
            session.commit();
        }
    }

    public static void updateNotice(String from_user, String to_user, int status) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);

            int user_id = UserMapperService.getIdByUsername(from_user);
            int friend_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.updateNotice(user_id, friend_id, status);
            session.commit();
        }
    }

    public static List<String> getFriends(String from_user) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            List<UserFriend> friends;
            // 查找全部的好友ID
            int from_id = UserMapperService.getIdByUsername(from_user);
            // 查找全部好友ID
            friends = userFriendMapper.getFriends(from_id, -1, "friend");
            // 根据ID查找姓名
            return friends.stream().map((item) -> {
                Integer friend_id = item.getFriend_id();
                return UserMapperService.getUserById(friend_id).getUsername();
            }).toList();
        }
    }

    public static List<String> getFriendsPending(String to_user) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            List<UserFriend> friends;
            // 查找全部的好友ID
            int to_id = UserMapperService.getIdByUsername(to_user);
            // 查找全部好友ID
            friends = userFriendMapper.getFriends(-1, to_id, "pending");
            // 根据ID查找姓名
            List<String> friends_username = friends.stream().map((item) -> {
                Integer friend_id = item.getUser_id();
                return UserMapperService.getUserById(friend_id).getUsername();
            }).toList();
            return friends_username;
        }
    }

    public static List<String> getFriendsNotice(String user) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            int user_id = UserMapperService.getIdByUsername(user);
            List<UserFriend> notices = userFriendMapper.getNotices(user_id);
            return notices.stream().map((item)-> {
                Integer friend_id = item.getFriend_id();
                return UserMapperService.getUserById(friend_id).getUsername();
            }).toList();
        }
    }

    public static void removeFriends(String from_user, String to_user) {
        try(SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            int from_id = UserMapperService.getIdByUsername(from_user);
            int to_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.removeFriend(from_id, to_id);
            userFriendMapper.removeFriend(to_id, from_id);
            session.commit();
        }
    }
}
