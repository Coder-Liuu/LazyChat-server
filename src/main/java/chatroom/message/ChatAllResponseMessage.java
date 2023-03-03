package chatroom.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatAllResponseMessage extends Message{
    String content;
    String username;

    public ChatAllResponseMessage(String content, String username) {
        this.content = content;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return ChatAllResponseMessage;
    }
}
