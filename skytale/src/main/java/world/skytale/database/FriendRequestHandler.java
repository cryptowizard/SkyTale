package world.skytale.database;

import world.skytale.model.sendable.FriendRequest;

public interface FriendRequestHandler {

    boolean addFriendRequest(FriendRequest friendRequest);
}
