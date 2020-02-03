package world.database;

import world.skytale.model.sendable.ChatMessage;

public interface ChatMessageHandler {

   boolean addChatMessage(ChatMessage chatMessage);
   boolean removeChatMessage(ChatMessage chatMessage);
}
