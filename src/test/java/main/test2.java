package main;

import chatroom.dao.UserDao;
import chatroom.service.UserService;
import chatroom.service.UserServiceMemoryImp2;
import org.junit.Test;

import static chatroom.dao.UserDao.insertStatus;
import static chatroom.dao.UserDao.updatePending;

public class test2 {
    @Test
    public void test22() {
        // insertStatus("zhangsan","liuyang", "pending");
        // updatePending("zhangsan","liuyang");
        // insertStatus("liuyang", "zhangsan", "friend");

        // updatePending("zhangsan","liuyang");
        // int zhangsan = UserDao.getByUsername("zhangsan");
        // System.out.println(zhangsan);
        // UserService userService = new UserServiceMemoryImp2();
        // boolean zhangsan = userService.login("zhangsa", "23");
        // System.out.println(zhangsan);
    }
}
