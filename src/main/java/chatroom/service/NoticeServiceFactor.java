package chatroom.service;

import chatroom.message.NoticeResponseMessage;
import chatroom.session.SessionFactory;
import io.netty.channel.Channel;

public class NoticeServiceFactor {
    private static NoticeService noticeService = new NoticeServiceImpl();
    public static NoticeService getNoticeService() {
        return noticeService;
    }
}
