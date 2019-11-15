package world.skytale.model;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;

public class Chat {

    public ID chatID;
    public ID [] participantIDs;
    public String chatName;
    public SecretKey secretKey;
    public String chatImagePath;


    public static Chat startNewChat(String chatName, ID [] participantIDs)
    {
        Chat chat = new Chat();
        chat.chatID = ID.generateRandomID();
        chat.chatName = chatName;
        chat.participantIDs = participantIDs;
        chat.secretKey = AES.generateNewKey();
        chat.chatImagePath = "";
        return  chat;
    }
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
