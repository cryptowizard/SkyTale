package world.xfreemedia.databes.daos;


import javax.crypto.SecretKey;

import world.skytale.converters.SecretKeyConventer;
import world.skytale.model.ID;

public class Chat extends world.skytale.model.Chat {
    public long lastMessageTime = 0;
    public int newMessages=0;

    public Chat(String id, SecretKey fromString, String[] split, long lastMessageTime, int newMessages, String picture) {
        super();

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


    public String conversationKeyToString()
    {
        return SecretKeyConventer.toString(this.secretKey);
    }


    public long ID()
    {
        return this.chatID.toLong();
    }


}
