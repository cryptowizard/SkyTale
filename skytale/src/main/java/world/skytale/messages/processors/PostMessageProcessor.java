package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;

import world.database.PostHandler;
import world.skytale.MessageProcessingException;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Contact;
import world.skytale.model.proto.PostProto;

public class PostMessageProcessor implements MessageProcessor {


    private final PostHandler postHandler;

    public PostMessageProcessor(PostHandler postHandler) {
        this.postHandler = postHandler;
    }

    @Override
    public void processIncoming(VeryfiedMessage message) throws MessageProcessingException, IOException,  InvalidKeyException {

        if (!isAllowedToSharePosts(message.getSender())) {
            throw new MessageProcessingException("Contact can not share posts with this account");
        }
        Messages.Post postProto = Messages.Post.parseFrom(message.getMessageBytes());

        PostProto post = new PostProto(postProto, message.getMessageHeader().getMessageID());

        postHandler.addPost(post);
    }



    private static boolean isAllowedToSharePosts(Contact sender) {
        return (sender.isFriend() || sender.isObserved());
    }


}
