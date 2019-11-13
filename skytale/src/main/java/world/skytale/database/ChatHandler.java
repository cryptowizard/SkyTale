package world.skytale.database;

import world.skytale.model.Chat;
import world.skytale.model.ID;

public interface ChatHandler {

    Chat getChat(ID chatID) throws ChatNotFoundException;
    boolean addChat(Chat chat);
    boolean updateChat(Chat chat);


    class ChatNotFoundException extends Exception
    {
        public ChatNotFoundException(ID chatID)
        {
            super("Chat with ID: \""+chatID+"\" was not found in Database");
        }
    }

}
