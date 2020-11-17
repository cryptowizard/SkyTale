package world.database;

import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.FriendRequest;

public interface FriendRequestHandler {

    boolean addFriendRequest(FriendRequest friendRequest);
    public FriendRequest getFriendRequest(MessageID messageID) throws ItemNotFoundException;
    boolean removeFriendRequest(FriendRequest friendRequest);
}
