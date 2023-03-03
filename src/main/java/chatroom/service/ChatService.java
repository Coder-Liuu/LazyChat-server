package chatroom.service;

/**
 * 用户管理接口
 */
public interface ChatService {
    /**
     * 给全部在线用户发送消息
     * @param username 用户名
     */
    void ChatAll(String content, String username);
    void ChatToOne(String from_user, String to_user, String content);
}
