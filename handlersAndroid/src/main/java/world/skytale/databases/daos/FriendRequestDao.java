package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import java.util.Date;

import world.skytale.model.ID;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.FriendRequest;

public class FriendRequestDao implements FriendRequest {

    private final String reciversEmail;
    private final ID senderID;
    private final long time;

    public FriendRequestDao(String reciversEmail, ID senderID, long time, long recivedTime) {
        this.reciversEmail = reciversEmail;
        this.senderID = senderID;
        this.time = time;
        this.recivedTime = recivedTime;
    }

    private final long recivedTime;

    public FriendRequestDao(String reciversEmail, ID senderID, long time) {
        this.reciversEmail = reciversEmail;
        this.senderID = senderID;
        this.time = time;
        this.recivedTime = new Date().getTime();
    }

    @Override
    public String getReciversEmail() {
        return reciversEmail;
    }


    public long getRecivedTime()
    {
        return recivedTime;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
    return       new MessageID(senderID,time);
    }
}
