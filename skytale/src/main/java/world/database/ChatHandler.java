package world.database;

import world.skytale.model.Chat;
import world.skytale.model.implementations.ID;

public interface ChatHandler {

    Chat getChat(ID chatID) throws ItemNotFoundException;
    boolean addChat(Chat chat);
    boolean updateChat(Chat chat);
    boolean removeChat(Chat chat);




}
