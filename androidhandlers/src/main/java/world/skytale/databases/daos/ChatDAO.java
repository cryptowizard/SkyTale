package world.skytale.databases.daos;


import world.skytale.converters.SecretKeyConventer;
import world.skytale.model.Chat;
import world.skytale.model.ID;

public class ChatDAO extends world.skytale.model.Chat {
    public long lastMessageTime = 0;
    public int newMessages=0;

    public ChatDAO(long id, String secretKey , String participants, long lastMessageTime, int newMessages, String picture, String name) {
        super();
        this.chatID = new ID(id);
        this.secretKey = SecretKeyConventer.fromString(secretKey);
        this.participantIDs = participantsFromString(participants);
        this.lastMessageTime = lastMessageTime;
        this.newMessages = newMessages;
        this.chatImagePath = picture;
        this.chatName = name;

    }

    public ChatDAO(Chat chat) {
       super(chat);
       this.newMessages =0;
       this.lastMessageTime =0;
    }


    public String patricipantsToString()
    {
        String tmp="";
        for(ID id : this.participantIDs)
        {
            tmp+=";"+id.toString();
        }
        return tmp.substring(1);
    }

    public ID [] participantsFromString(String participantIDs){
        String [] split = participantIDs.split(";");
        ID [] ids = new ID[split.length];
        for(int i=0;i<split.length;i++)
        {
            ids[i]= new ID(split[i]);
        }
        return ids;
    }


    public String conversationKeyToString()
    {
        return SecretKeyConventer.toString(this.secretKey);
    }


    public long ID()
    {
        return this.chatID.toLong();
    }


}
