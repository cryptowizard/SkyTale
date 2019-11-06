package world.skytale.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 132126L;

    private PublicKeyId senderID;
    private long time;

    private String message;
    private String [] attachments;
}
