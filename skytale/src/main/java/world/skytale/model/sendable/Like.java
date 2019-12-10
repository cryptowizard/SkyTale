package world.skytale.model.sendable;

import world.skytale.model.ID;

public interface Like extends Sendable{
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
