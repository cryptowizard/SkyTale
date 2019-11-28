package world.skytale.database;

import world.skytale.model.AvaiableMessages.FriendRequest;

public interface FriendRequestHandler {

    boolean addFriendRequest(FriendRequest friendRequest);
}
