package chatroom.message;

public class LoginResponseMessage extends Message {
    boolean success;
    String msg;

    public LoginResponseMessage(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
