package world.skytale.messages.senders;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.database.DatabaseHandler;
import world.database.ItemNotFoundException;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.FriendRequestResponseBuilder;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.ProfilePage;
import world.skytale.model.implementations.FriendsChat;
import world.skytale.model.sendable.FriendRequest;

public class FriendRequestResponseSender extends MessageSender {


    private FriendRequest friendRequest;
    private Contact newFriendsContact;
    private Chat friendsChat;

    public void sendFriendRequestAcceptResponse(FriendRequest friendRequest) throws Exception {
        this.friendRequest = friendRequest;
        setFriendsContact(friendRequest.getMessageID().getSenderID());
        setFriendsChat(friendRequest.getMessageID().getSenderID());
        send();
    }

    private void setFriendsContact(ID friedsID) throws ItemNotFoundException {
        this.newFriendsContact = databaseHandler.getContactsHandler().getContact(friedsID);
    }

    private void setFriendsChat(ID friendsID) throws ItemNotFoundException {
        this.friendsChat = createFriendsChat(friendsID);
    }

    private Chat createFriendsChat(ID friendsID) throws ItemNotFoundException {
        ProfilePage friendsProfile = databaseHandler.getProfilePageHandler().getProfilePage(friendRequest.getMessageID().getSenderID());
        ID myID = account.getUserContact().getID();
        SecretKey secretKey = AES.generateNewKey();

        FriendsChat friendsChat = new FriendsChat(myID,friendsProfile, secretKey);
        return friendsChat;
    }


    public FriendRequestResponseSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        super(mailTransporter, databaseHandler, attachmentFactory);
    }

    @Override
    protected boolean addToDatabase() {
        databaseHandler.getContactsHandler().setContactIsFriend(newFriendsContact.getID(),true);
        databaseHandler.getChatHandler().addChat(friendsChat);
        return true;
    }

    @Override
    protected boolean removeFromDatabase() {
        databaseHandler.getContactsHandler().setContactIsFriend(newFriendsContact.getID(),false);
        databaseHandler.getChatHandler().removeChat(friendsChat);
        return true;
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws Exception {
        FriendRequestResponseBuilder builder = new FriendRequestResponseBuilder(attachmentFactory,account);
        builder.setMessage(friendRequest,newFriendsContact,friendsChat.getSecretKey());
        return builder.makeDownloadedMail();
    }

    @Override
    protected List<String> getRecipientAdresses() {
        ArrayList<String> tmp =  new ArrayList<String>();
        tmp.add(newFriendsContact.getAdress());
        return tmp;
    }
}
