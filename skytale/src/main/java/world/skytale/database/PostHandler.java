package world.skytale.database;

import world.skytale.model2.ID;
import world.skytale.model2.Post;

public interface PostHandler {
    Post getPost(ID sendersID, long time);
    public boolean addPost(Post post);
}
