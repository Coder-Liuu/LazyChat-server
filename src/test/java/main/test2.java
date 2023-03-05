package main;

import chatroom.service.UserService;
import chatroom.service.UserServiceMemoryImp2;
import org.junit.Test;

public class test2 {
    @Test
    public void test22() {
        UserService userService = new UserServiceMemoryImp2();
        boolean zhangsan = userService.login("zhangsa", "23");
        System.out.println(zhangsan);
    }
}
