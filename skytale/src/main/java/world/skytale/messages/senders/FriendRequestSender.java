package world.skytale.messages.senders;

import java.util.ArrayList;
import java.util.List;

import world.database.DatabaseHandler;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.FriendRequestBuilder;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.proto.FriendRequestImp;

public class FriendRequestSender extends MessageSender {

    private FriendRequest friendRequest;


    public void sendFriendRequest(FriendRequest friendRequest) throws Exception {
        this.friendRequest = friendRequest;
        send();
    }

    public void sendFriendRequest(String reciversEmail) throws Exception {
        this.friendRequest = createNewFriendRequest(reciversEmail);
        send();
    }

    private FriendRequest createNewFriendRequest(String reciversEmail)
    {
        return new FriendRequestImp(this.account.getUserContact().getID(), reciversEmail);
    }
    public FriendRequestSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        super(mailTransporter, databaseHandler, attachmentFactory);
    }

    @Override
    protected boolean addToDatabase() {
        return databaseHandler.getFriendRequestHandler().addFriendRequest(friendRequest);
    }

    @Override
    protected boolean removeFromDatabase() {
        return databaseHandler.getFriendRequestHandler().removeFriendRequest(friendRequest);
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws Exception {
        FriendRequestBuilder friendRequestBuilder = new FriendRequestBuilder(attachmentFactory,account);
        friendRequestBuilder.setMessage(friendRequest);
        return friendRequestBuilder.makeDownloadedMail();
    }

    @Override
    protected List<String> getRecipientAdresses() {
      ArrayList<String> list =  new ArrayList<String>();
      list.add(friendRequest.getReciversEmail());
      return list;
    }
}
