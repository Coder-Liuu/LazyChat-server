package chatroom.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequestMessage extends Message{
    String username;
    String password;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
