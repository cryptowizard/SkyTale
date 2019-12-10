package world.skytale.database;

import world.skytale.model.ID;
import world.skytale.model.sendable.Post;

public interface PostHandler {
    Post getPost(ID sendersID, long time);
    public boolean addPost(Post post);
}
