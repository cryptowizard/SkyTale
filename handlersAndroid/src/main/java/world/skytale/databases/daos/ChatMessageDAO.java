package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import java.util.Date;

import world.skytale.model.ID;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.ChatMessage;

public class ChatMessageDAO implements ChatMessage {

    private final ID senderID;
    private final long time;
    private final ID chatID;

    private DisplayableDAO displayable;

    private long recivedTime;


    public long getRecivedTime() {
        return recivedTime;
    }

    public void setRecivedTime(long recivedTime) {
        this.recivedTime = recivedTime;
    }

  ;

    public ChatMessageDAO(ID senderID, long time, ID chatID, DisplayableDAO displayable) {
        this.senderID = senderID;
        this.time = time;
        this.chatID = chatID;
        this.displayable = displayable;
        this.recivedTime = new Date().getTime();

    }

    public ChatMessageDAO(ID senderID, long time, ID chatID) {
        this.senderID = senderID;
        this.time = time;
        this.chatID = chatID;
        this.recivedTime = new Date().getTime();
    }

    public void setDisplayable(DisplayableDAO displayable) {
        this.displayable = displayable;
    }

    @Override
    public DisplayableDAO getDisplayable() {
        return displayable;
    }


    @NonNull
    @Override
    public ID getChatID() {
        return chatID;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return  new MessageID(senderID,time);
    }
}
