package world.skytale.model.implementations;

import androidx.annotation.NonNull;

import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.Displayable;

public class ChatMessageImp implements ChatMessage {

    private final ID chatID;
    private final  MessageID messageID;

    private Displayable displayable;

    public ChatMessageImp(ID chatID, ID senderID, long time)
    {
        this.chatID =chatID;
        this.messageID = new MessageID(senderID,time);
    }

    public ChatMessageImp(ID chatID, MessageID messageID) {
        this.chatID = chatID;
        this.messageID  = messageID;
    }


    public void setDisplayable(Displayable displayable) {
        this.displayable = displayable;
    }

    @NonNull
    @Override
    public ID getChatID() {
        return this.chatID;
    }

    @Override
    public Displayable getDisplayable() {
        return this.displayable;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }
}
