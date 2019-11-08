package world.skytale.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    public static final long serialVersionUID = 132126L;

    public ID senderID;
    public long time;
    public String message;
    public String [] attachments;


    public ChatMessage(ChatMessage chatMessage)
    {
        this.senderID = chatMessage.senderID;
        this.time = chatMessage.time;
        this.message = chatMessage.message;
        this.attachments = chatMessage.attachments.clone();
    }

    public ChatMessage() {

    }
}
