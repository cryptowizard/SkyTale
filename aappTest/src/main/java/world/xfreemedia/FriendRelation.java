package world.xfreemedia;

import world.database.ItemNotFoundException;
import world.skytale.model.implementations.FriendRequestImp;
import world.skytale.model.sendable.FriendRequest;

public class FriendRelation {

    public static void makeUsersFriends(MockedUser mockedUser0, MockedUser mockedUser1) throws Exception {
       if(usersAreFriends(mockedUser0,mockedUser1)) return;

        FriendRequest friendRequest = new FriendRequestImp(mockedUser0.getCurrentAccount().getUserContact().getID(), mockedUser1.getEmail());
        mockedUser0.outgoinMessageProcessor.sendFriendRequest(friendRequest);
        mockedUser1.outgoinMessageProcessor.acceptFriendRequest(friendRequest);
    }


    public static boolean usersAreFriends(MockedUser user0, MockedUser user1)
    {
        try {
            return user0.skyTaleDatabaseHandler.getContactsHandler().getContact(user1.getUserID()).isFriend() && user1.skyTaleDatabaseHandler.getContactsHandler().getContact(user0.getUserID()).isFriend();
        } catch (ItemNotFoundException e) {
            return false;
        }
    }
}
