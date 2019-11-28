package world.skytale.model.AvaiableMessages;

import world.skytale.model.ID;

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
