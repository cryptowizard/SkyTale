package world.skytale.database;

import world.skytale.model.Chat;
import world.skytale.model.ID;

public interface ChatHandler {

    Chat getChat(ID chatID) throws  ChatNotFoundExceptoin;
    boolean addChat(Chat chat);
    boolean updateChat(Chat chat);


    class ChatNotFoundExceptoin extends Exception
    {
        public ChatNotFoundExceptoin(ID chatID)
        {
            super("Contact with ID: \""+chatID+"\" was not found in Database");
        }
    }

}
