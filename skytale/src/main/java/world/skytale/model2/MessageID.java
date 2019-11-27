package world.skytale.model2;


/**
 * Each message like ChatMessage, Post, Comment or FriendRequest can be uniquelly identified by
 * sender ID and tie
 */
public class MessageID {

    private final ID senderID;
    private final long time;

    public MessageID(ID senderID, long time) {
        this.senderID = senderID;
        this.time = time;
    }

    public MessageID(long senderID, long time)
    {
        this.senderID = new ID(senderID);
        this.time = time;
    }

    public ID getSenderID() {
        return senderID;
    }

    public long getTime() {
        return time;
    }


}
