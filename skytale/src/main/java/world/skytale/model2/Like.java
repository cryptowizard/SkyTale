package world.skytale.model2;

public interface Like {
    ID getSenderID();
    long getTime();

    ID getPostSenderID();
    long getPostTime();

    enum RefrenceType {
        POST,
        COMMENT,
        MESSAGE
    }
}
