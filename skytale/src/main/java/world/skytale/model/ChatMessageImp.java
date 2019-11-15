package world.skytale.model;

import java.io.Serializable;
import java.util.ArrayList;

import world.skytale.model2.Attachment;
import world.skytale.model2.ID;

public class ChatMessageImp implements world.skytale.model2.ChatMessage,  Serializable {

    public static final long serialVersionUID = 132126L;

    protected ID senderID;
    protected long time;
    protected String message;
    protected ArrayList<Attachment> attachments;


    public ChatMessageImp()
    {
        message = "";
        attachments = new ArrayList<Attachment>();
    }

    public ChatMessageImp(ID senderID, long time, String message, ArrayList<Attachment> attachments) {
        this.senderID = senderID;
        this.time = time;
        this.message = message;
        this.attachments = attachments;
    }


    public ChatMessageImp(ChatMessageImp chatMessage)
    {
        this.senderID = chatMessage.senderID;
        this.time = chatMessage.time;
        this.message = chatMessage.message;
        this.attachments = (ArrayList<Attachment>) chatMessage.attachments.clone();
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
        return attachments;
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
