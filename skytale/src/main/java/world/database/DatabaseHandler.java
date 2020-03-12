package world.database;

public interface DatabaseHandler {

     AccountProvider getAccountProvider();
     ContactsHandler getContactsHandler();
     ChatHandler getChatHandler();
     ChatMessageHandler getChatMessageHandler();
     PostHandler getPostHandler();
     EncryptionKeyHandler getEncryptionKeyHandler();
     FriendRequestHandler getFriendRequestHandler();
     ProfilePageHandler getProfilePageHandler();
     CommentsHandler getCommentsHandler();
 }
