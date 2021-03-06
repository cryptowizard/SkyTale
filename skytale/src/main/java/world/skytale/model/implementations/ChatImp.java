package world.skytale.model.implementations;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.skytale.model.Attachment;
import world.skytale.model.Chat;

public class ChatImp implements Chat {

    public ID chatID;
    public ID [] participantIDs;
    public String chatName;
    public  SecretKey secretKey;
    public Attachment chatImage;


    public ChatImp(ID chatID, ID[] participantIDs, String chatName, SecretKey secretKey, Attachment chatImage) {
        this.chatID = chatID;
        this.participantIDs = participantIDs;
        this.chatName = chatName;
        this.secretKey = secretKey;
        this.chatImage = chatImage;
    }

    public ChatImp(ChatImp chat)
    {
        this.chatID = chat.chatID;
        this.participantIDs = chat.participantIDs;
        this.chatName = chat.chatName;
        this.secretKey = chat.secretKey;
        this.chatImage = chat.chatImage;
    }

    public ChatImp(ID id, SecretKey fromString, ID[] participantsFromString) {
    }

    public static ChatImp startNewChat(String chatName, ID [] participantIDs)
    {

        ID chatId  = ID.generateRandomID();
        SecretKey secretKey = AES.generateNewKey();
        ChatImp chat = new ChatImp(chatId, participantIDs, chatName, secretKey,null);
        return  chat;
    }

    public ID getChatID() {
        return chatID;
    }

    public ID[] getParticipantIDs() {
        return participantIDs;
    }

    public String getChatName() {
        return chatName;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }




    public Attachment getChatImage() {
        return chatImage;
    }


    public void setParticipantIDs(ID[] participantIDs) {
        this.participantIDs = participantIDs;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setChatImage(Attachment chatImage) {
        this.chatImage = chatImage;
    }

    public void setChatID(ID chatID) {
        this.chatID = chatID;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
