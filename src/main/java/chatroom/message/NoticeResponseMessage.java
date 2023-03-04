package chatroom.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeResponseMessage extends Message{
    int notice_type;
    String from_user;
    String to_user;
    String reason;

    public NoticeResponseMessage(int notice_type, String from_user, String to_user, String reason) {
        this.notice_type = notice_type;
        this.from_user = from_user;
        this.to_user = to_user;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return NoticeResponseMessage;
    }
}
