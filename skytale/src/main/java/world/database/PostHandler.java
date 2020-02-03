package world.database;

import world.skytale.model.MessageID;
import world.skytale.model.sendable.Post;

public interface PostHandler {
    Post getPost(MessageID messageID) throws ItemNotFoundException;
    public boolean addPost(Post post);
    public boolean removePost(Post post);
}
