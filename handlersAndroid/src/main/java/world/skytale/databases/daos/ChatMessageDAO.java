package world.skytale.databases.daos;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.model.attachments.FileAttachment;
import world.skytale.model2.Attachment;
import world.skytale.model2.ChatMessage;
import world.skytale.model2.ID;

public class ChatMessageDAO implements ChatMessage {


    public ID senderID;
    public long time;
    public String message;
    public long recivedTime;
    public String[] attachments;


    public ChatMessageDAO() {
        super();
        this.recivedTime = new Date().getTime();
    }

    public ChatMessageDAO(ChatMessage chatMessage) {

        this.senderID = chatMessage.getSenderID();
        this.message = chatMessage.getMessage();
        this.time = chatMessage.getTime();
        this.attachments = new String[0];
        this.recivedTime = new Date().getTime();
    }

    public String atachmentsToString() {
//        if(getAttachments()==null||getAttachments().size()==0)
//        {
//            return "";
//        }
//        String tmp = "";
//        for(String attachment : getAttachments())
//        {
//            tmp+=";"+attachment;
//        }
//            return tmp.substring(1);
        return "";
    }

    public static ChatMessageDAO recreateMessage(long senderID, String message, long time, String attachments, long recivedTime) {

        ChatMessageDAO tmp = new ChatMessageDAO();
        tmp.senderID = new ID(senderID);
        tmp.message = message;
        tmp.time = time;
        tmp.attachments = attachments.split(";");
        tmp.recivedTime = recivedTime;
        return tmp;
    }


    @Override
    public ID getSenderID() {
        return senderID;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ArrayList<Attachment> getAttachments() {
        return fromPathList(this.attachments);
    }


    private static ArrayList<Attachment> fromPathList(String[] filePaths) {
        ArrayList<Attachment> list = new ArrayList<>();
        for (String path : filePaths) {
            FileAttachment fileAttachment = FileAttachment.fromPath(path);
            if (fileAttachment != null) {
                list.add(fileAttachment);


            }

        }
        return list;
    }

    }
