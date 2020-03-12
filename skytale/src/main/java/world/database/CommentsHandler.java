package world.database;

import world.skytale.model.MessageID;
import world.skytale.model.sendable.Comment;

public interface CommentsHandler {
    Comment getComment(MessageID messageID) throws ItemNotFoundException;
    boolean addComment(Comment comment);
    boolean removeComment(Comment comment);

}
