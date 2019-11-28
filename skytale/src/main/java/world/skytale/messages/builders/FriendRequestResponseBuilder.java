package world.skytale.messages.builders;

import world.skytale.database.DatabaseHandler;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.AvaiableMessages.FriendRequest;

public class FriendRequestResponseBuilder extends MailBuilder {

    public static final String TYPE_TAG = "FRIEND_RESP";

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     * @param databaseHandler
     */
    public FriendRequestResponseBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        super(attachmentFactory, databaseHandler);
    }

    private VeryfiedMessage buildVerifiedMessage(FriendRequest friendRequest)
    {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(), friendRequest.getTime());

        return null;
    }


}
