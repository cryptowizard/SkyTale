package world.xfreemedia;

import world.skytale.model.proto.FriendRequestImp;
import world.skytale.model.sendable.FriendRequest;

public class FriendRelation {

    public static void makeUsersFriends(MockedUser mockedUser0, MockedUser mockedUser1) throws Exception {
       FriendRequest friendRequest = new FriendRequestImp(mockedUser0.getCurrentAccount().getUserContact().getID(), mockedUser1.getEmail());

        mockedUser0.outgoinMessageProcessor.sendFriendRequest(friendRequest);
        mockedUser1.outgoinMessageProcessor.acceptFriendRequest(friendRequest);
    }
}
