package chatroom.dao;

import chatroom.dao.entity.UserFriend;
import chatroom.dao.mapper.UserFriendMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class UserFriendMapperService {
    public static void insertStatus(String from_user, String to_user, String status) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            int user_id = UserMapperService.getIdByUsername(from_user);
            int friend_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.insertStatus(user_id, friend_id, status);
            session.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePending(String from_user, String to_user) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            int user_id = UserMapperService.getIdByUsername(from_user);
            int friend_id = UserMapperService.getIdByUsername(to_user);
            userFriendMapper.updatePending(user_id, friend_id);
            session.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getFriends(String from_user) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            UserFriendMapper userFriendMapper = session.getMapper(UserFriendMapper.class);
            List<UserFriend> friends;
            // 查找全部的好友ID
            int from_id = UserMapperService.getIdByUsername(from_user);
            // 查找全部好友ID
            friends = userFriendMapper.getFriends(from_id, -1, "friend");
            // 根据ID查找姓名
            List<String> friends_username = friends.stream().map((item) -> {
                Integer friend_id = item.getFriend_id();
                return UserMapperService.getUserById(friend_id).getUsername();
            }).toList();
            return friends_username;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> getFriendsPending(String to_user) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
