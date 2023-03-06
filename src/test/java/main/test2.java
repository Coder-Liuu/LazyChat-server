package main;

import chatroom.dao.UserFriendMapperService;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.List;

public class test2 {
    @SneakyThrows
    @Test
    public void test22() {
        // insertStatus("zhangsan","liuyang", "pending");
        // updatePending("zhangsan","liuyang");
        // insertStatus("liuyang", "zhangsan", "friend");
        //
        // List<String> friends = UserDao2.getFriends("zhangsan", "friend");
        // System.out.println(friends);


        // SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        // SqlSession session = sessionFactory.openSession();
        // UserMapper userMapper = session.getMapper(UserMapper.class);
        // User zhangsan = userMapper.selectByUsername("zhangsan");
        // System.out.println(zhangsan);
        // User user = UserMapperService.queryUser("zhangsan");
        // System.out.println(user);

        // UserFriendMapperService.insertStatus("zhangsan", "lisi", "pending");
        // List<String> friends = UserFriendMapperService.getFriends("zhangsan", null, "friend");
        List<String> friends = UserFriendMapperService
            .getFriendsPending("lisi");
        System.out.println(friends);
    }
}
