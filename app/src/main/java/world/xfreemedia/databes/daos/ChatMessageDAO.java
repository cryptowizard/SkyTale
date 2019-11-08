package world.xfreemedia.databes.daos;

import world.skytale.model.ID;

public class ChatMessageDAO extends world.skytale.model.ChatMessage {


    public long recivedTime;


    public String aatachmentsToString()
    {
        String tmp = "";
        for(String attachment : attachments)
        {
            tmp+=";"+attachment;
        }
            return tmp.substring(1);
    }

    public static ChatMessageDAO recreateMessage(long senderID, String message, long time, String attachments, long recivedTime)
    {
        ChatMessageDAO tmp = new ChatMessageDAO();
        tmp.senderID=new ID(senderID);
        tmp.message = message;
        tmp.time = time;
        tmp.attachments = attachments.split(";");
        tmp.recivedTime = recivedTime;
        return tmp;
    }


}
