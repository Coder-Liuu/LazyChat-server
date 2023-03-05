package chatroom.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImp1 implements UserService{
    private Map<String, String> allUserMap = new ConcurrentHashMap<>();

    {
        allUserMap.put("zhangsan", "123");
        allUserMap.put("lisi", "123");
        allUserMap.put("wangwu", "123");
        allUserMap.put("zhaoliu", "123");
        allUserMap.put("qianqi", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String pass = allUserMap.get(username);
        return password.equals(pass);
    }
}
