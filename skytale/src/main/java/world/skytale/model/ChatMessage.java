package world.skytale.model;

import java.io.Serializable;
import java.util.ArrayList;

import world.skytale.model.attachments.Attachment;

public class ChatMessage implements Serializable {
    public static final long serialVersionUID = 132126L;

    public ID senderID;
    public long time;
    public String message;
    public ArrayList<Attachment> attachments;


    public ChatMessage()
    {
        message = "";
        attachments = new ArrayList<Attachment>();
    }

    public ChatMessage(ID senderID, long time, String message, ArrayList<Attachment> attachments) {
        this.senderID = senderID;
        this.time = time;
        this.message = message;
        this.attachments = attachments;
    }


    public ChatMessage(ChatMessage chatMessage)
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




}
