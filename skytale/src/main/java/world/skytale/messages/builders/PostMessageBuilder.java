package world.skytale.messages.builders;

import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.proto.PostProto;
import world.skytale.model.sendable.Post;

public class PostMessageBuilder extends MailBuilder {
    public static final String TYPE_TAG = "POST";

    Post post;

    public void setPost(Post post)
    {
        this.post = post;
    }
    public PostMessageBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }

    @Override
    protected void checkIfMessageIsSet() throws Exception {
        if(post==null) throw new Exception("Post is null");
    }

    @Override
    protected MessageHeader buildMessageHeader() {
      return new MessageHeader(TYPE_TAG, post.getMessageID());
    }

    @Override
    protected byte[] buildMessageBytes() throws Exception {
        Messages.Post postProto = PostProto.toProtoMessage(post);
        return postProto.toByteArray();
    }





}
