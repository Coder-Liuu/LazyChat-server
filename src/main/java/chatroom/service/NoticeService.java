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
     */
    void NoticeAddUserPending(String from_user, String to_user);

    void NoticeAddUserFriend(String from_user, String to_user);
    void NoticeAddUserRefuse(String from_user, String to_user);

    void NoticeRemoveUser(String from_user, String to_user);
    /**
     * 好友上线
     */
}
