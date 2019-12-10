package world.skytale.messages.builders;

import java.io.IOException;

import world.skytale.database.DatabaseHandler;
import world.skytale.message.Messages;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.ProfilePage;
import world.skytale.proto.FriendRequestProto;

public class FriendRequestBuilder extends MailBuilder {

    public static final String TYPE_TAG = "FRIEND_REQ";

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     * @param databaseHandler
     */


    public FriendRequestBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        super(attachmentFactory, databaseHandler);
    }


    public DownloadedMail makeDownloadedMail(FriendRequest friendRequest) throws IOException {
        VeryfiedMessage veryfiedMessage = buildVerifiedMessage(friendRequest);
        return super.makeDownloadedMail(veryfiedMessage);
    }

    private VeryfiedMessage buildVerifiedMessage(FriendRequest friendRequest)
    {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(), friendRequest.getTime());

        ProfilePage myProfilePage = getAccount().getUserProfilePage();
        Messages.FriendRequest friendRequestProto = FriendRequestProto.toProto(myProfilePage, friendRequest.getReciversEmail());

        VeryfiedMessage veryfiedMessage =  new VeryfiedMessage(messageHeader, friendRequestProto.toByteArray());
        return veryfiedMessage;
    }
}
