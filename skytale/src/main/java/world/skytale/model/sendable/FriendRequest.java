package world.skytale.model.sendable;

import world.skytale.model.ID;

public interface FriendRequest extends Sendable {
    ID getSendersID();
    long getTime();

    String getReciversEmail();
}
