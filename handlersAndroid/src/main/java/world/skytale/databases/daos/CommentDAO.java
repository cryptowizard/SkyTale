package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import world.skytale.model.MessageID;
import world.skytale.model.sendable.Comment;

public class CommentDAO implements Comment {

    public final MessageID messageID;
    public final MessageID postID;

    public MessageID replyID;
    public DisplayableDAO displayable;

    public CommentDAO(MessageID messageID, MessageID postID) {
        this.messageID = messageID;
        this.postID = postID;
    }

    public CommentDAO(MessageID messageID, MessageID postID, MessageID replyID, DisplayableDAO displayable) {
        this.messageID = messageID;
        this.postID = postID;
        this.replyID = replyID;
        this.displayable = displayable;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }

    @NonNull
    @Override
    public MessageID getPostID() {
        return postID;
    }

    @Override
    public MessageID getReplyID() {
        return replyID;
    }

    public void setReplyID(MessageID replyID) {
        this.replyID = replyID;
    }

    @Override
    public DisplayableDAO getDisplayable() {
        return displayable;
    }

    public void setDisplayable(DisplayableDAO displayable) {
        this.displayable = displayable;
    }
}
