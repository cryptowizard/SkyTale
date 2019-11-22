package world.skytale.model;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.model2.Attachment;
import world.skytale.model2.ID;
import world.skytale.model2.Post;

public class PostImp implements Post {

    public final ID sendersID;
    public final long time;
    public ID ordinalSenderID;
    public String text;



    public ArrayList<Attachment> attachments;
    public String link;

    public PostImp(ID sendersID)
    {
        this.sendersID = sendersID;
        this.time = new Date().getTime();
    }

    public PostImp(ID sendersID, long time) {
        this.sendersID = sendersID;
        this.time = time;
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
        return attachments;
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

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setLink(String link) {
        this.link = link;
    }




}
