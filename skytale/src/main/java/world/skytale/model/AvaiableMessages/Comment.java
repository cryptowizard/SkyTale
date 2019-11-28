package world.skytale.model.AvaiableMessages;

import world.skytale.model.ID;

public interface Comment {

    ID getSenderID();
    long getTime();

    ID getPostID();
    long getPosTime();
}
