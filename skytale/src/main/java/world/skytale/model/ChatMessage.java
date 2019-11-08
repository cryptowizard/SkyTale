package world.skytale.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    public static final long serialVersionUID = 132126L;

    public ID senderID;
    public long time;
    public String message;
    public String [] attachments;
}
