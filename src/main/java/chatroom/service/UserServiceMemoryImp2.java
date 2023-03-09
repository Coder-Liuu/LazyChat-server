package chatroom.service;

import chatroom.dao.UserMapperService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceMemoryImp2 implements UserService {

    @Override
    public boolean login(String username, String password) {
        return UserMapperService.userLogin(username, password);
    }
}