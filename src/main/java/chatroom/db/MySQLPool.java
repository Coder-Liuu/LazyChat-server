package chatroom.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;

public class MySQLPool {
    DataSource dataSource;

    public MySQLPool(){
        // 加载配置文件
        InputStream inputStream = MySQLPool.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Mysql 初始化错误");
        }

        // 获取连接池数据源
        dataSource = setupDataSource(properties);
    }

    private DataSource setupDataSource(Properties properties) {
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
}
