package world.skytale.messages.processors;

import com.google.protobuf.InvalidProtocolBufferException;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

import world.skytale.MessageProcessingException;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.cyphers.ECCipher;
import world.database.ChatHandler;
import world.database.ContactsHandler;
import world.database.DatabaseHandler;
import world.database.FriendRequestHandler;
import world.database.ItemNotFoundException;
import world.database.ProfilePageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Account;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.ProfilePage;
import world.skytale.model.implementations.FriendsChat;
import world.skytale.model.proto.ProfilePageProto;
import world.skytale.model.sendable.FriendRequest;

public class FriendRequestResponseProcessor implements MessageProcessor {


    private final FriendRequestHandler friendRequestHandler;
    private final ContactsHandler contactsHandler;
    private final ProfilePageHandler profilePageHandler;
    private final ChatHandler chatHandler;
    private final Account account;

    public FriendRequestResponseProcessor (DatabaseHandler databaseHandler)
    {
            friendRequestHandler = databaseHandler.getFriendRequestHandler();
            contactsHandler = databaseHandler.getContactsHandler();
            profilePageHandler = databaseHandler.getProfilePageHandler();
            chatHandler = databaseHandler.getChatHandler();
            account = databaseHandler.getAccountProvider().getCurrentAccount();
    }


    @Override
    public void processIncoming(VeryfiedMessage message) throws MessageProcessingException, ItemNotFoundException, InvalidProtocolBufferException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        FriendRequest friendRequest = friendRequestHandler.getFriendRequest(new MessageID(account.getUserContact().getID(), message.getMessageHeader().getTime()));

        veryfiRequestWasSendFromThisAccount(friendRequest, message.getSender().getAdress());

        Messages.FriendRequestAcceptRespone friendRequestAcceptRespone = Messages.FriendRequestAcceptRespone.parseFrom(message.getMessageBytes());

        ProfilePage friendsProfilePage = new ProfilePageProto(friendRequestAcceptRespone.getProfilePage(), message.getSender().getID());
        byte [] encryptedChatKeyBytes = friendRequestAcceptRespone.getChatEncryptionKey().toByteArray();
        SecretKey chatKey = decryptEncryptedChatKey(encryptedChatKeyBytes);
        Chat friendsChat = new FriendsChat(account.getUserContact().getID(), friendsProfilePage, chatKey);


        addResultsToDatabase(message.getSender(), friendsProfilePage, friendsChat, friendRequest);

    }

    private void veryfiRequestWasSendFromThisAccount (FriendRequest friendRequest, String sendersEmail) throws MessageProcessingException, ItemNotFoundException {

        /**
         * @Todo
         * Diffrent exception type!! with specyfied message
         * Addding message to message processing exceptino
         */
        if(!friendRequest.getReciversEmail().equals(sendersEmail)) throw  new MessageProcessingException("sendersEmail");

    }

    private SecretKey decryptEncryptedChatKey(byte [] chatKey) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
      byte [] keyBytes =  ECCipher.decrypt(account.getPrivateKey(),chatKey);
      return SecretKeyConventer.fromBytes(keyBytes);
    }


    private void addResultsToDatabase(Contact newFriend, ProfilePage friendsProfilePage, Chat friendsChat, FriendRequest friendRequest)
    {
        contactsHandler.addContact(newFriend);
        contactsHandler.setContactIsFriend(friendsProfilePage.getConstactID(),true);
        profilePageHandler.addProfilePage(friendsProfilePage);
        friendRequestHandler.removeFriendRequest(friendRequest);
        chatHandler.addChat(friendsChat);
    }
}
