package world.skytale.messages;

import java.util.List;

import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.EncryptionKey;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.sendable.Post;

/**
 * This Interface that aggregates all the actions user may take
 *
 */
public interface OutgoinMessageProcessor {

    public void sendChatMessage(ChatMessage chatMessage);
    public void sendChatMessage(ChatMessage chatMessage, Chat chat);

    public void sharePost(Post post, List<String> emailAdresses);
    public void shareEncryptedPost(Post post, List<String> adressList, EncryptionKey postEncryptionKey);


    public void sendFriendRequest(FriendRequest friendRequest);
    public void sendFriendRequest(String email);


    public void acceptFriendRequest(FriendRequest friendRequest);


    public void sherePostEncryptionKey(EncryptionKey encryptionKey, Contact reciver);
    public void sharePostEncryptionKey(EncryptionKey encryptionKey, List<Contact> recivers);





}
