package world.NetworkTest;


import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import world.database.ItemNotFoundException;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.ID;
import world.skytale.model.implementations.FriendsChat;
import world.skytale.model.proto.FriendRequestImp;
import world.skytale.model.sendable.FriendRequest;
import world.testMethods.ContactTableTest;
import world.testMethods.ProfilePageHandlerTests;
import world.xfreemedia.FriendRelation;
import world.xfreemedia.MockedNetwork;
import world.xfreemedia.MockedUser;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class FriendRequestTest {


    MockedUser mockedUser0;
    MockedUser mockedUser1;
    FriendRequest friendRequest;
    Context context  =  InstrumentationRegistry.getTargetContext();

    @Before
    public void Before()
    {
       MockedNetwork mockedNetwork = new MockedNetwork(2, context);
        mockedUser0 = mockedNetwork.getUser(0);
        mockedUser1 = mockedNetwork.getUser(1);


     friendRequest = new FriendRequestImp(mockedUser0.getCurrentAccount().getUserContact().getID(), mockedUser1.getEmail());

    }



    @Test
    public void sendingRequest() throws Exception {
        mockedUser0.outgoinMessageProcessor.sendFriendRequest(friendRequest);

        veryfyFriendRequestWasSend(friendRequest);
    }

    private void veryfyFriendRequestWasSend(FriendRequest friendRequest) throws ItemNotFoundException {

        FriendRequest sendFriendRequest = mockedUser1.skyTaleDatabaseHandler.getFriendRequestHandler().getFriendRequest(friendRequest.getMessageID());
        FriendRequest recivedFriendRequest = mockedUser1.skyTaleDatabaseHandler.getFriendRequestHandler().getFriendRequest(friendRequest.getMessageID());

        assertEquals(friendRequest.getReciversEmail(),recivedFriendRequest.getReciversEmail());
        assertEquals(friendRequest.getReciversEmail(),sendFriendRequest.getReciversEmail());
        ContactTableTest.testIFcontactIsInDatabase(mockedUser0.getCurrentAccount().getUserContact(),mockedUser1.skyTaleDatabaseHandler);

    }


    @Test
    public void veryfiAcceptRespone() throws Exception {
        FriendRelation.makeUsersFriends(mockedUser0,mockedUser1);

        usersAreFriends();
        usersHaveEachOtherProfilePage();
        bthUsersHaveFriendsChat();
    }

    public void usersAreFriends() throws ItemNotFoundException {
        Contact friend1 = mockedUser0.skyTaleDatabaseHandler.getTableContacts().getContact(mockedUser1.getCurrentAccount().getUserContact().getID());

        assertTrue(friend1.isFriend());

        Contact friend0 = mockedUser1.skyTaleDatabaseHandler.getTableContacts().getContact(mockedUser0.getCurrentAccount().getUserContact().getID());
        assertTrue(friend0.isFriend());

    }

    public void usersHaveEachOtherProfilePage() throws ItemNotFoundException {

        ProfilePageHandlerTests.veryfyProfilePageIsInDatbase(mockedUser0.getCurrentAccount().getUserProfilePage(),mockedUser1.skyTaleDatabaseHandler);
        ProfilePageHandlerTests.veryfyProfilePageIsInDatbase(mockedUser1.getCurrentAccount().getUserProfilePage(),mockedUser0.skyTaleDatabaseHandler);

    }

    public void bthUsersHaveFriendsChat() throws ItemNotFoundException {
        ID chatID = FriendsChat.generateFriendsChatID(new ID[]{mockedUser1.getUserID(), mockedUser0.getUserID()});

        Chat chat0 = mockedUser0.skyTaleDatabaseHandler.getChatHandler().getChat(chatID);
        Chat chat1 = mockedUser1.skyTaleDatabaseHandler.getChatHandler().getChat(chatID);

        assertEquals(chat0.getSecretKey(),chat1.getSecretKey());
    }


}
