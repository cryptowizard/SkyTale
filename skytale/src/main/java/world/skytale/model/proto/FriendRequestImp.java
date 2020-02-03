package world.skytale.model.proto;

import androidx.annotation.NonNull;

import world.skytale.model.ID;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.FriendRequest;

public class FriendRequestImp implements FriendRequest {

    private final MessageID messageID;
    private final String reciversEmail;

    public FriendRequestImp(  MessageID messageID, String reciversEmail) {
        this.messageID = messageID;
        this.reciversEmail = reciversEmail;
    }
    public FriendRequestImp(ID senderID, String reciversEmail)
    {
        this.messageID = new MessageID(senderID);
        this.reciversEmail= reciversEmail;
    }


    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }

    @Override
    public String getReciversEmail() {
        return reciversEmail;
    }
}
