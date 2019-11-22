package world.skytale.database;

public interface DatabaseHandler {

    AccountProvider getAccountProvider();
     ContactsHandler getTableContacts();
     ChatHandler getChatHandler();
     ChatMessageHandler getChatMessageHandler();
     PostHandler getPostHandler();
}
