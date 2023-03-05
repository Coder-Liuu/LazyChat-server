package chatroom.service;

import chatroom.db.ConnectionFactor;
import com.alibaba.druid.util.JdbcUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

@Slf4j
public class UserServiceMemoryImp2 implements UserService {
    private boolean queryDatabase(String login_username, String login_password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactor.getConnection();
            // 创建查询语句
            String sql = "select * from user where username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, login_username);

            // 执行查询
            rs = pstmt.executeQuery();

            // 处理结果集 如果没有直接返回True
            if(!rs.next()) {
                register(conn, login_username, login_password);
                return true;
            }
            // 如果有密码正确返回True, 如果密码不对返回False
            String password = rs.getString("password");
            return Objects.equals(login_password, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭结果集和语句
            JdbcUtils.close(rs);
            JdbcUtils.close(pstmt);
            JdbcUtils.close(conn);
        }
    }

    private static void register(Connection conn, String username, String password) throws SQLException {
        // 创建插入语句
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";

        // 创建语句对象
        java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        // 执行插入
        pstmt.executeUpdate();

        // 关闭语句
        pstmt.close();
    }

    @Override
    public boolean login(String username, String password) {
        return queryDatabase(username, password);
    }
}