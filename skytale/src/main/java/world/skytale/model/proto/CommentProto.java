package world.skytale.model.proto;

import androidx.annotation.NonNull;

import world.skytale.message.Messages;
import world.skytale.model.Displayable;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.Comment;

public class CommentProto implements Comment {

    private final Messages.Comment protoMessage;
    private final MessageID messageID;

    public CommentProto(Messages.Comment protoMessage, MessageID messageID) {
        this.protoMessage = protoMessage;
        this.messageID = messageID;
    }

    public static Messages.Comment toProtoMessage(Comment comment)
    {
        Messages.Comment.Builder builder =  Messages.Comment.newBuilder();
        builder.setPostSenderID(comment.getPostID().getSenderID().toLong());
        builder.setPostTime(comment.getPostID().getTime());
        builder.setDisplayable(DisplayableProto.toProtoMessage(comment.getDisplayable()));
        builder.setReplyID(comment.getReplyID().getSenderID().toLong());
        builder.setReplyTime(comment.getReplyID().getTime());

        return builder.build();
    }


    @NonNull
    @Override
    public MessageID getPostID() {
        return new MessageID(protoMessage.getPostSenderID(),protoMessage.getPostTime());
    }

    @Override
    public MessageID getReplyID() {
        return new MessageID(protoMessage.getReplyID(),protoMessage.getReplyTime());
    }

    @Override
    public Displayable getDisplayable() {
        return new DisplayableProto(protoMessage.getDisplayable());
    }


    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }
}
