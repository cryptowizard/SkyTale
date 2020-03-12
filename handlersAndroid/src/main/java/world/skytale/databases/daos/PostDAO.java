package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import java.util.Date;

import world.skytale.model.ID;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.Post;

public class PostDAO implements Post {


    public final ID sendersID;
    public final long time;

    public ID ordinalSenderID;

    public DisplayableDAO getDisplayableDAO() {
        return displayableDAO;
    }

    public void setDisplayableDAO(DisplayableDAO displayableDAO) {
        this.displayableDAO = displayableDAO;
    }

    public DisplayableDAO displayableDAO;

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

    @Override
    public DisplayableDAO getDisplayable() {
        return displayableDAO;
    }



    public void setOrdinalSenderID(ID ordinalSenderID) {
        this.ordinalSenderID = ordinalSenderID;
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

    @NonNull
    @Override
    public MessageID getMessageID() {
        return       new MessageID(sendersID,time);
    }
}
