package chatroom.service;

/**
 * 通知管理接口
 */
public interface NoticeService {
    /**
     * 给某个用户发送添加好友的通知
     *
     * @param from_user
     * @param to_user
     * @param notice_type
     */
    void NoticeAddUser(String from_user, String to_user, int notice_type);
    /**
     * 好友上线
     */
    // void Notice
}
