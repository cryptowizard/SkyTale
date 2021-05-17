package world.skytale.databases.model;

import androidx.annotation.NonNull;

import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Reply;

public class ReplyDAO implements Reply {

    public final MessageID messageID;
    public final MessageID postID;

    public MessageID replyID;
    public DisplayableDAO displayable;

    public ReplyDAO(MessageID messageID, MessageID postID) {
        this.messageID = messageID;
        this.postID = postID;
    }

    public ReplyDAO(MessageID messageID, MessageID postID, MessageID replyID, DisplayableDAO displayable) {
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
    public MessageID getOrginalContentID() {
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
