package world.NetworkTest;


import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import world.database.ItemNotFoundException;
import world.skytale.databases.daos.DisplayableDAO;
import world.skytale.model.ID;
import world.skytale.model.implementations.PostImp;
import world.skytale.model.sendable.Post;
import world.xfreemedia.FriendRelation;
import world.xfreemedia.MockedNetwork;
import world.xfreemedia.MockedUser;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class SharePostTest {


    MockedNetwork mockedNetwork;
    Context context  =  InstrumentationRegistry.getTargetContext();
    Post post;



    @Before
    public void Before() throws Exception {
        mockedNetwork = new MockedNetwork(3, context);
        FriendRelation.makeUsersFriends(mockedNetwork.getUser(0), mockedNetwork.getUser(1));
        post = createMockedPost(mockedNetwork.getUser(0).getUserID());
    }


    @Test
    public void sharePostWithCustomList() throws Exception {
        ArrayList<String> adresses = new ArrayList<String>();
        adresses.add(mockedNetwork.getUser(1).getEmail());
        adresses.add(mockedNetwork.getUser(2).getEmail());

        mockedNetwork.getUser(0).outgoinMessageProcessor.sharePost(post,adresses);

        veryfyUserRecivedPost(mockedNetwork.getUser(1),post);
        veryfyUserRecivedPost(mockedNetwork.getUser(1),post);
        verfyfyUserDoesNotHaveAPost(mockedNetwork.getUser(2),post);

    }


    @Test
    public void SharePostWithAllFriends() throws Exception {

      //  mockedNetwork.getUser(0).outgoinMessageProcessor.s;

    }


    private static void veryfyUserRecivedPost(MockedUser mockedUser, Post post) throws ItemNotFoundException
    {
      Post recivedPost =   mockedUser.skyTaleDatabaseHandler.getPostHandler().getPost(post.getMessageID());
      assertTrue(recivedPost!=null);
    }



    private static void verfyfyUserDoesNotHaveAPost(MockedUser mockedUser, Post post)  {

        try {
            mockedUser.skyTaleDatabaseHandler.getPostHandler().getPost(post.getMessageID());
            fail();
        } catch (ItemNotFoundException e) {
            e.printStackTrace();

        }

    }


    private static Post createMockedPost(ID senderID)
    {
      PostImp post = new PostImp(senderID);
     DisplayableDAO displayableDAO = new DisplayableDAO();
     displayableDAO.setText("HELLOW");
     post.displayable = displayableDAO;

      return post;
    }








}
