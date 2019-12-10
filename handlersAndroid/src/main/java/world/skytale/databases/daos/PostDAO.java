package world.skytale.databases.daos;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.databases.files.FileAttachment;
import world.skytale.model.Attachment;
import world.skytale.model.ID;
import world.skytale.model.sendable.Post;

public class PostDAO implements Post {


    public final ID sendersID;
    public final long time;

    public ID ordinalSenderID;

    public String [] attachments = new String[0];
    public String link = "";
    public String text = "";

    public long receivedTime;
    public int numberOfComments =0;
    public boolean liked=false;


    public PostDAO(ID sendersID, long time) {
        this.sendersID = sendersID;
        this.time = time;

        this.receivedTime = new Date().getTime();

    }


    public ID getSenderID() {
        return sendersID;
    }

    public long getTime() {
        return time;
    }

    @Override
    public ID getOrdinalSendersID() {
        if(this.ordinalSenderID ==null)
        {
            return this.sendersID;
        }
        return ordinalSenderID;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Attachment> getAttachments() {
        return fromPathList(this.attachments);
    }

    public String getLink() {
        return link;
    }

    public void setOrdinalSenderID(ID ordinalSenderID) {
        this.ordinalSenderID = ordinalSenderID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttachments(String [] attachments)
    {
        this.attachments = attachments;
    }

    public void setLink(String link) {
        this.link = link;
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

    public boolean isLiked()
    {
        return liked;
    }


    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
