package world.skytale.model.sendable;

import world.skytale.model.ID;

public interface Comment extends Sendable {

    ID getSenderID();
    long getTime();

    ID getPostID();
    long getPosTime();
}
