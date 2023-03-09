package chatroom.message;

public interface Constant {
    // notice_type
    // 1: 请求好友
    // 2: 同意请求
    // 3: 拒绝请求
    // 4: 删除好友
    int NOTICE_FRIEND_REQ = 1;
    int NOTICE_FRIEND_AGREE = 2;
    int NOTICE_FRIEND_REFUSE = 3;
    int NOTICE_FRIEND_REMOVE = 4;
    // 10: 返回好友列表
    int NOTICE_FRIEND_LIST = 10;
    // 20: 用户上线通知
    // 21: 用户下线通知
    int NOTICE_ONLINE = 20;
    int NOTICE_OFFLINE = 21;
}
