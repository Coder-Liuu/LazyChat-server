package chatroom.service;


public class UserServiceFactory {
    private static UserService userService = new UserServiceMemoryImp2();
    public static UserService getUserService() {
        return userService;
    }
}
