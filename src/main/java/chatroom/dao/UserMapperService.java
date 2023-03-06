package chatroom.dao;

import chatroom.dao.entity.User;
import chatroom.dao.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class UserMapperService {
    private static User queryUser(String login_username) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.selectByUsername(login_username);
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void register(String username, String password) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            User user = new User(username, password);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            session.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean UserLogin(String login_username, String login_password) {
        User user = queryUser(login_username);
        if (user == null) {
            register(login_username, login_password);
            return true;
        }
        return user.getPassword().equals(login_password);
    }

    public static int getIdByUsername(String to_user) {
        if(to_user == null) return -1;
        User user = queryUser(to_user);
        if (user == null) return -1;
        return user.getId();
    }

    public static User getUserById(int id) {
        try (SqlSession session = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml")).openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.selectById(id);
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
