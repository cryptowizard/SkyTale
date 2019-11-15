package world.skytale.database;

import world.skytale.model2.ChatMessage;
import world.skytale.model2.ID;

public interface ChatMessageHandler {

   boolean addChatMessage(ChatMessage chatMessage, ID chatID);
}
