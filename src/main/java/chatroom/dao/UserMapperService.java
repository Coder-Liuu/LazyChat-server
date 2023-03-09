package chatroom.dao;

import chatroom.dao.entity.User;
import chatroom.dao.mapper.DBSqlSessionFactor;
import chatroom.dao.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

public class UserMapperService {
    private static User queryUser(String login_username) {
        try (SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.selectByUsername(login_username);
        }
    }

    private static void register(String username, String password) {
        try (SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            User user = new User(username, password);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            session.commit();
        }
    }

    public static User getUserById(int id) {
        try (SqlSession session = DBSqlSessionFactor.getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.selectById(id);
        }
    }

    public static boolean userLogin(String login_username, String login_password) {
        User user = queryUser(login_username);
        if (user == null) {
            register(login_username, login_password);
            return true;
        }
        return user.getPassword().equals(login_password);
    }

    public static int getIdByUsername(String to_user) {
        if (to_user == null) return -1;
        User user = queryUser(to_user);
        if (user == null) return -1;
        return user.getId();
    }
}
