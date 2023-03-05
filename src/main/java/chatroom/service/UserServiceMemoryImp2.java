package chatroom.service;

import chatroom.db.ConnectionFactor;
import com.alibaba.druid.util.JdbcUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

import static chatroom.dao.UserDao.queryUser;

@Slf4j
public class UserServiceMemoryImp2 implements UserService {

    @Override
    public boolean login(String username, String password) {
        return queryUser(username, password);
    }
}