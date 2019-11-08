package world.skytale.database;

import world.skytale.model.ChatMessage;
import world.skytale.model.ID;

public interface ChatMessageHandler {

   boolean addChatMessage(ChatMessage chatMessage, ID chatID);
}
