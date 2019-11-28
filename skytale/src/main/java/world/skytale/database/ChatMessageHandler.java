package world.skytale.database;

import world.skytale.model.AvaiableMessages.ChatMessage;

public interface ChatMessageHandler {

   boolean addChatMessage(ChatMessage chatMessage);
}
