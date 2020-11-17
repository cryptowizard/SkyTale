package world.database;

import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Reply;

public interface CommentsHandler {
    Reply getComment(MessageID messageID) throws ItemNotFoundException;
    boolean addComment(Reply reply);
    boolean removeComment(Reply reply);

}
