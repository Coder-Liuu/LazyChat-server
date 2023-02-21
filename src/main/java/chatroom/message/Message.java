package chatroom.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Message implements Serializable {
    private int messageType;
    public abstract int getMessageType();

    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    // 定义数据类型
    public static int LoginRequestMessage = 0;
    public static int LoginResponseMessage = 1;
    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
    }
}
