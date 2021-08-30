package world.skytale.messages.processors;

import com.google.protobuf.InvalidProtocolBufferException;

import java.security.InvalidKeyException;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.database.PostHandler;
import world.skytale.MessageProcessingException;
import world.skytale.cyphers.AES;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Contact;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.KeyID;
import world.skytale.model.proto.PostProto;

public class EncryptedPostProcessor implements MessageProcessor {

    private final PostHandler postHandler;
    private final EncryptionKeyHandler encryptionKeyHandler;

    public EncryptedPostProcessor(PostHandler postHandler, EncryptionKeyHandler encryptionKeyHandler) {
        this.postHandler = postHandler;
        this.encryptionKeyHandler = encryptionKeyHandler;
    }

    @Override
    public void processIncoming(VeryfiedMessage message) throws MessageProcessingException, InvalidProtocolBufferException, ItemNotFoundException, InvalidKeyException {
        if (!isAllowedToSharePosts(message.getSender())) {
            throw new MessageProcessingException("Contact can not share posts with this account");
        }

        Messages.EncryptedPost encryptedPostProto = Messages.EncryptedPost.parseFrom(message.getMessageBytes());

        long keySenderID = encryptedPostProto.getKeyID().getSenderID();
        int keyType = encryptedPostProto.getKeyID().getEncryptionKeyType();
        KeyID keyID = new KeyID(keySenderID, keyType);

        EncryptionKey encryptionKey = encryptionKeyHandler.getEncryptionKey(keyID);

        byte[] c = encryptedPostProto.getEncryptedPostBytes().toByteArray();
        byte[] postBytes = AES.decrypt(encryptionKey.getKey(), c, message.getMessageHeader().getMessageID());


        Messages.Post postProto = Messages.Post.parseFrom(postBytes);

        PostProto post = new PostProto(postProto, message.getMessageHeader().getMessageID());

        postHandler.addPost(post);
    }

    private static boolean isAllowedToSharePosts(Contact sender) {
        return (sender.isFriend() || sender.isObserved());
    }


}
