package world.skytale.databases.daos;

public class MessageID {

    public long senderID;
    public long time;

    public MessageID(long senderID, long time) {
        this.senderID = senderID;
        this.time = time;
    }

    public long getSenderID() {
        return senderID;
    }

    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
