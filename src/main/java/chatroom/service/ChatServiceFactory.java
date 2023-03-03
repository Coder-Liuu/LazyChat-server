package chatroom.service;

public class ChatServiceFactory {
    private static ChatService chatService = new ChatServerImpl();
    public static ChatService getChatService() {
        return chatService;
    }
}
