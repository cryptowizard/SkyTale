package world.skytale.database;

import world.skytale.model.sendable.ChatMessage;

public interface ChatMessageHandler {

   boolean addChatMessage(ChatMessage chatMessage);
}
