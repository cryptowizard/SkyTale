package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import world.skytale.MessageProcessingException;
import world.skytale.database.ChatHandler;
import world.skytale.database.PostHandler;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.proto.PostImp;
import world.skytale.proto.attachments.ProtoAttachment;
import world.skytale.model.Attachment;
import world.skytale.model.Contact;
import world.skytale.model.ID;
import world.skytale.model.AvaiableMessages.Post;

public class PostMessageProcessor implements MessageProcessor {


    private final PostHandler postHandler;

    public PostMessageProcessor(PostHandler postHandler) {
        this.postHandler = postHandler;
    }

    @Override
    public void processIncoming(VeryfiedMessage message) throws MessageProcessingException, IOException, ChatHandler.ChatNotFoundException, InvalidKeyException {

        if(!isAllowedToSharePosts(message.getContactType()))
        {
            throw new MessageProcessingException("Contact can not share posts with this account");
        }
        Messages.Post postProto = Messages.Post.parseFrom(message.getMessageBytes());

        Post post = buildPost(postProto, message.getMessageHeader());

        postHandler.addPost(post);
    }

    private static Post buildPost(Messages.Post post, MessageHeader messageHeader)
    {
        ID sendersID = messageHeader.getSenderID();
        ID orginalSendersID = new ID(post.getOrginalSendersID());

        ArrayList<Attachment> attachments  = ProtoAttachment.fromProtoList(post.getAttachmentsList());
        PostImp postImp = new PostImp(sendersID, messageHeader.getTime());
        postImp.setOrdinalSenderID(orginalSendersID);
        postImp.setAttachments(attachments);
        postImp.setLink(post.getLink());
        postImp.setText(post.getPostText());

        return postImp;
        }

        private static boolean isAllowedToSharePosts(int contactType)
        {
            if(contactType == Contact.TYPE_FOLLOWED || contactType == Contact.TYPE_FOLLOWED)
            {
            return true;
        }
        else
        {
            return false;
        }
    }
}
