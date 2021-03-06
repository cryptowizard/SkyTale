package world.skytale.model.implementations;

public class KeyID {

    public static final int KEY_TYPE_FRIENDS = 1;
    public static final int KEY_TYPE_FOLLOWERS = 2;

    private final int keyType;
    private final long senderID;


    public KeyID(long senderID, int keyType) {
        this.keyType = keyType;
        this.senderID = senderID;
    }

    public KeyID(ID senderID, int keyType)
    {
        this.senderID = senderID.toLong();
        this.keyType = keyType;
    }

    public ID getSenderID()
    {
        return new ID(senderID);
    }

    public int getKeyType()
    {
        return keyType;
    }
}
