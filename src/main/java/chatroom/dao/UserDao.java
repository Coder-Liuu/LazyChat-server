package chatroom.dao;

import chatroom.db.ConnectionFactor;
import com.alibaba.druid.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserDao {
    public static boolean queryUser(String login_username, String login_password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactor.getConnection();
            // 创建查询语句
            String sql = "select * from users where username = ?";
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

    public static int getByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactor.getConnection();
            // 创建查询语句
            String sql = "select * from users where username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            // 执行查询
            rs = pstmt.executeQuery();

            // 处理结果集 如果没有直接返回True
            if(rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭结果集和语句
            JdbcUtils.close(rs);
            JdbcUtils.close(pstmt);
            JdbcUtils.close(conn);
        }
    }

    public static void insertStatus(String from_user, String to_user, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactor.getConnection();
            // 创建插入语句
            String sql = "INSERT INTO user_friends (user_id, friend_id, status) VALUES (?, ?, ?)";

            // 创建语句对象
            pstmt = conn.prepareStatement(sql);

            // 设置参数
            pstmt.setInt(1, getByUsername(from_user));
            pstmt.setInt(2, getByUsername(to_user));
            pstmt.setString(3, status);

            // 执行插入
            pstmt.executeUpdate();

            // 关闭语句
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭结果集和语句
            JdbcUtils.close(rs);
            JdbcUtils.close(pstmt);
            JdbcUtils.close(conn);
        }
    }

    public static void updatePending(String from_user, String to_user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactor.getConnection();
            // 创建插入语句
            String sql = "UPDATE user_friends set status = ? where user_id = ? and friend_id = ? ";

            // 创建语句对象
            pstmt = conn.prepareStatement(sql);

            // 设置参数
            pstmt.setString(1, "friend");
            pstmt.setInt(2, getByUsername(from_user));
            pstmt.setInt(3, getByUsername(to_user));

            // 执行插入
            pstmt.executeUpdate();

            // 关闭语句
            pstmt.close();
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
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

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
}
