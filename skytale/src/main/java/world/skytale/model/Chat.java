package world.skytale.model;

import javax.crypto.SecretKey;

public class Chat {

    public ID chatID;
    public ID [] participantIDs;
    public String chatName;
    public SecretKey secretKey;
    public String chatImagePath;


    public Chat()
    {

    }
    public  Chat (Chat chat)
    {
        this.chatID = chat.chatID;
        this.participantIDs = chat.participantIDs;
        this.chatName = chat.chatName;
        this.secretKey = chat.secretKey;
        this.chatImagePath = chat.chatImagePath;
    }
}
