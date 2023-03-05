package main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

public class JdbcExampleWithPool {

    public static void main(String[] args) throws SQLException, IOException {
        // 加载配置文件
        InputStream inputStream = JdbcExampleWithPool.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        // 获取连接池数据源
        DataSource dataSource = setupDataSource(properties);

        // 获取数据库连接
        Connection conn = dataSource.getConnection();

        // 查询数据库
        // queryDatabase(conn);

        // 插入数据
        // insertData(conn, "John", "123");

        // 关闭连接
        conn.close();

        System.out.println("Goodbye!");
    }

    private static DataSource setupDataSource(Properties properties) {
        org.apache.commons.dbcp2.BasicDataSource basicDataSource = new org.apache.commons.dbcp2.BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
        basicDataSource.setUrl(properties.getProperty("jdbc.url"));
        basicDataSource.setUsername(properties.getProperty("jdbc.username"));
        basicDataSource.setPassword(properties.getProperty("jdbc.password"));
        basicDataSource.setInitialSize(Integer.parseInt(properties.getProperty("jdbc.initialSize")));
        basicDataSource.setMaxTotal(Integer.parseInt(properties.getProperty("jdbc.maxActive")));
        basicDataSource.setMaxIdle(Integer.parseInt(properties.getProperty("jdbc.maxIdle")));
        basicDataSource.setMinIdle(Integer.parseInt(properties.getProperty("jdbc.minIdle")));
        basicDataSource.setMaxWaitMillis(Long.parseLong(properties.getProperty("jdbc.maxWait")));
        return basicDataSource;
    }

    // public static void queryDatabase(Connection conn) throws SQLException {
    //     // 创建查询语句
    //     String sql = "SELECT id, username, password FROM users";
    //
    //     // 创建语句对象
    //     Statement stmt = conn.createStatement();
    //
    //     // 执行查询
    //     ResultSet rs = stmt.executeQuery(sql);
    //
    //     // 处理结果集
    //     while (rs.next()) {
    //         int id = rs.getInt("id");
    //         String username = rs.getString("username");
    //         String password = rs.getString("password");
    //
    //         // 输出结果
    //         System.out.print("ID: " + id);
    //         System.out.print(", Name: " + username);
    //         System.out.println(", Password: " + password);
    //     }
    //
    //     // 关闭结果集和语句
    //     rs.close();
    //     stmt.close();
    // }

}