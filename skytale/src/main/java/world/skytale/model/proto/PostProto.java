package world.skytale.model.proto;

import androidx.annotation.NonNull;

import world.skytale.message.Messages;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.Displayable;
import world.skytale.model.sendable.Post;

public class PostProto implements Post {

   private final Messages.Post protoMessage;
   private final MessageID messageID;

    public PostProto(Messages.Post post,MessageID messageID) {
        this.protoMessage = post;
        this.messageID = messageID;

    }


    public PostProto(Post post)
    {
        this.protoMessage = toProtoMessage(post);
        this.messageID = post.getMessageID();
    }


    public static Messages.Post toProtoMessage(Post post)
    {

        Messages.Post protoPost = Messages.Post.newBuilder()
                .setOrginalSendersID(post.getOrdinalSendersID().toLong())
                .setDisplayable(DisplayableProto.toProtoMessage(post.getDisplayable()))
                .build();

        return protoPost;
    }


    @Override
    public ID getOrdinalSendersID() {
        return new ID(protoMessage.getOrginalSendersID());
    }

    @Override
    public Displayable getDisplayable() {
        return new DisplayableProto(protoMessage.getDisplayable());
    }


    @NonNull
    @Override
    public MessageID getMessageID() {
        return this.messageID;
    }
}
