package world.skytale.model.proto;

import androidx.annotation.NonNull;

import world.skytale.message.Messages;
import world.skytale.model.Displayable;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Reply;

public class ReplyProto implements Reply {

    private final Messages.Comment protoMessage;
    private final MessageID messageID;


    public ReplyProto(Messages.Comment protoMessage, MessageID messageID) {
        this.protoMessage = protoMessage;
        this.messageID = messageID;
    }

    public static Messages.Comment toProtoMessage(Reply reply)
    {
        Messages.Comment.Builder builder =  Messages.Comment.newBuilder();
        builder.setPostSenderID(reply.getOrginalContentID().getSenderID().toLong());
        builder.setPostTime(reply.getOrginalContentID().getTime());
        builder.setDisplayable(DisplayableProto.toProtoMessage(reply.getDisplayable()));
        builder.setReplyID(reply.getReplyID().getSenderID().toLong());
        builder.setReplyTime(reply.getReplyID().getTime());

        return builder.build();
    }


    @NonNull
    @Override
    public MessageID getOrginalContentID() {
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
