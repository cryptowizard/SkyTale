package world.skytale.messages.builders;

import world.skytale.database.DatabaseHandler;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.sendable.Sendable;

public class FriendRequestResponseBuilder extends MailBuilder {

    public static final String TYPE_TAG = "FRIEND_RESP";

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     * @param databaseHandler
     */
    public FriendRequestResponseBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        super(attachmentFactory, databaseHandler);
    }

    @Override
    protected String getTypeTag() {
        return TYPE_TAG;
    }

    @Override
    protected byte[] buildMessageBytes(Sendable sendable) {
        return new byte[0];
    }

    private VeryfiedMessage buildVerifiedMessage(FriendRequest friendRequest)
    {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(), friendRequest.getTime());

        return null;
    }


}
