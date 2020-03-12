package world.skytale.messages.builders;

import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.ProfilePage;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.proto.FriendRequestProto;

public class FriendRequestBuilder extends MailBuilder {

    public static final String TYPE_TAG = "FRIEND_REQ";

    private FriendRequest friendRequest;

    public void setMessage(FriendRequest friendRequest)
    {
        this.friendRequest = friendRequest;
    }


    public FriendRequestBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }


    @Override
    protected void checkIfMessageIsSet() throws Exception {
        if(friendRequest==null) throw new Exception("Exception");

    }

    @Override
    protected MessageHeader buildMessageHeader() {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, friendRequest.getMessageID());
        return messageHeader;
    }

    @Override
    protected byte[] buildMessageBytes() throws Exception {
        ProfilePage myProfilePage = getAccount().getUserProfilePage();
        Messages.FriendRequest friendRequestProto = FriendRequestProto.toProto(myProfilePage, friendRequest.getReciversEmail());
        return friendRequestProto.toByteArray();
    }




}
