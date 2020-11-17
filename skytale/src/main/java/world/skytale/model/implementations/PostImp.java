package world.skytale.model.implementations;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.Displayable;
import world.skytale.model.sendable.Post;

public class PostImp implements Post {

    private final MessageID messageID;
    public ID ordinalSenderID;
    public Displayable displayable;



    public ArrayList<Attachment> attachments;
    public String link;

    public PostImp(ID sendersID)
    {
       this.messageID = new MessageID(sendersID);

    }

    public PostImp(ID sendersID, long time) {
      this.messageID = new MessageID(sendersID,time);
    }


    @Override
    public ID getOrdinalSendersID() {
        if(this.ordinalSenderID ==null)
        {
            return this.messageID.getSenderID();
        }
        return ordinalSenderID;
    }

    @Override
    public Displayable getDisplayable() {
        return null;
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


    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }
}
