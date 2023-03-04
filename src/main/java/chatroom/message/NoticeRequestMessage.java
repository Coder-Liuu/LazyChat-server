package chatroom.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeRequestMessage extends Message{
    int notice_type;
    String from_user;
    String to_user;

    public NoticeRequestMessage(int notice_type, String from_user, String to_user) {
        this.notice_type = notice_type;
        this.from_user = from_user;
        this.to_user = to_user;
    }

    @Override
    public int getMessageType() {
        return NoticeRequestMessage;
    }
}
