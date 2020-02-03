package world.database.Tables;

import org.junit.Test;

import world.database.ItemNotFoundException;
import world.database.PostHandler;
import world.skytale.databases.daos.DisplayableDAO;
import world.skytale.model.ID;
import world.skytale.model.implementations.PostImp;
import world.skytale.model.sendable.Post;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PostHandlerTest extends DatabaseHandlerTest {

    PostHandler postHandler = getDatabaseHandler().getPostHandler();


    @Test
    public void addPost() throws ItemNotFoundException {

        Post post = makePost();
        postHandler.addPost(post);

        Post postFromDB = postHandler.getPost(post.getMessageID());
        veryfyTwoPostsAreSame(post,postFromDB);
    }


    @Test(expected = ItemNotFoundException.class)
    public void removePost() throws ItemNotFoundException {

        Post post = makePost();
        postHandler.addPost(post);

        postHandler.removePost(post);

        postHandler.getPost(post.getMessageID());

    }


    private static Post makePost()
    {
        ID id = ID.generateRandomID();
        PostImp postImp = new PostImp(id);
        DisplayableDAO displayableDAO = new DisplayableDAO();
        displayableDAO.setText("Hello this is some random post");
        postImp.displayable = displayableDAO;
        return postImp;
    }

    public static void veryfyTwoPostsAreSame(Post post, Post post1)
    {
        assertTrue(post.getMessageID().getSenderID().equals(post1.getMessageID().getSenderID()));
        assertEquals(post.getMessageID().getTime(), post1.getMessageID().getTime());
        assertEquals(post.getOrdinalSendersID(), post1.getOrdinalSendersID());

    }
}