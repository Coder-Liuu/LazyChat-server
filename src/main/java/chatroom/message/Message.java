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
    public static int ChatAllRequestMessage = 2;
    public static int ChatAllResponseMessage = 3;
    public static int ChatToOneRequestMessage = 4;
    public static int ChatToOneResponseMessage = 5;
    public static int NoticeRequestMessage = 6;
    public static int NoticeResponseMessage = 7;
    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

    // 添加完常量之后 必须 添加进map里面取
    static {
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClasses.put(ChatAllRequestMessage, ChatAllRequestMessage.class);
        messageClasses.put(ChatAllResponseMessage, ChatAllResponseMessage.class);
        messageClasses.put(ChatToOneRequestMessage, ChatToOneRequestMessage.class);
        messageClasses.put(ChatToOneResponseMessage, ChatToOneResponseMessage.class);
        // 通知
        messageClasses.put(NoticeRequestMessage, NoticeRequestMessage.class);
        messageClasses.put(NoticeResponseMessage, NoticeResponseMessage.class);
    }
}
