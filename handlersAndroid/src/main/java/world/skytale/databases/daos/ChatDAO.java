package world.skytale.databases.daos;


import javax.crypto.SecretKey;

import world.skytale.converters.SecretKeyConventer;
import world.skytale.model.attachments.FileAttachment;
import world.skytale.model2.ID;

public class ChatDAO implements world.skytale.model2.Chat {
    public long lastMessageTime;
    public int newMessages;

    public ID chatID;
    public ID [] participantIDs;
    public SecretKey secretKey;
    public String chatName;
    public FileAttachment chatImage;

    @Override
    public ID getChatID() {
        return chatID;
    }

    @Override
    public ID[] getParticipantIDs() {
        return participantIDs;
    }

    @Override
    public SecretKey getSecretKey() {
        return secretKey;
    }

    @Override
    public String getChatName() {
        return chatName;
    }

    @Override
    public FileAttachment getChatImage() {
        return this.chatImage;
    }

    public ChatDAO()
    {
        this.newMessages=0;
        this.lastMessageTime =0;
    }

    public  ChatDAO(long id, String secretKey , String participants, long lastMessageTime, int newMessages, String picturePath, String name) {
        this.chatID = new ID(id);
        this.secretKey = SecretKeyConventer.fromString(secretKey);
        this.participantIDs = participantsFromString(participants);
        this.chatName = name;
        this.lastMessageTime = lastMessageTime;
        this.newMessages = newMessages;
        this.chatImage =  FileAttachment.fromPath(picturePath);
    }
    private static ID [] participantsFromString(String participantIDs){
        String [] split = participantIDs.split(";");
        ID [] ids = new ID[split.length];
        for(int i=0;i<split.length;i++)
        {
            ids[i]= new ID(split[i]);
        }
        return ids;
    }


    public  String participantsToString()
    {
        String tmp="";
        for(ID id : this.getParticipantIDs())
        {
            tmp+=";"+id.toString();
        }
        return tmp.substring(1);
    }


    public String conversationKeyToString()
    {
        return SecretKeyConventer.toString(this.getSecretKey());
    }


}
