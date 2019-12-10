package world.skytale.proto;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.ID;

public class ChatMessageImp implements ChatMessage,  Serializable {

    public static final long serialVersionUID = 132126L;

    private final ID chatID;
    protected ID senderID;
    protected long time;
    protected String message;
    protected ArrayList<Attachment> attachments;


    public ChatMessageImp(ID chatID)
    {
        this.chatID = chatID;
        message = "";
        attachments = new ArrayList<Attachment>();
    }

    public ChatMessageImp(ID chatID, ID senderID, long time, String message, ArrayList<Attachment> attachments) {
        this.chatID = chatID;
        this.senderID = senderID;
        this.time = time;
        this.message = message;
        this.attachments = attachments;
    }


    @NonNull
    @Override
    public ID getChatID() {
        return chatID;
    }

    public ID getSenderID() {
        return senderID;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setSenderID(ID senderID) {
        this.senderID = senderID;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }




}
