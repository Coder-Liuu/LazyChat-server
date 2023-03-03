package chatroom.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatToOneResponseMessage extends Message{
    String from_user;
    String to_user;
    String content;

    public ChatToOneResponseMessage(String from_user, String to_user, String content) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return ChatToOneResponseMessage;
    }
}
