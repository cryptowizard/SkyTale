package world.skytale.messages.builders;

import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.AES;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.EncryptionKey;
import world.skytale.model.sendable.Post;


//@ToDO HERE THIS IS TO BE DONE NEXT!!!!
// @ToDo Rethink encrypted messages packageing hmmm
public class EncryptedPostMessageBuilder extends PostMessageBuilder {

    public static final String TYPE_TAG = "ENC_POST";

    Post post;
    EncryptionKey encryptionKey;


    public EncryptedPostMessageBuilder(AttachmentFactory attachmentFactory, Account account)
    {
        super(attachmentFactory,account);
    }

    public void setPost(Post post)
    {
        this.post = post;
    }

    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    protected void checkIfMessageIsSet() throws Exception {
        if(encryptionKey == null) throw  new Exception("Encryption Key is null");
        super.checkIfMessageIsSet();
    }


    @Override
    protected byte[] buildMessageBytes() throws Exception {
        Messages.EncryptedPost encryptedPost = Messages.EncryptedPost.newBuilder()
                .setEncryptedPostBytes(ByteConverter.toByteString(getEnccryptedPostBytes()))
                .setKeyID(buildEncryptionKeyID())
                .build();

        return encryptedPost.toByteArray();
    }

    private byte [] getEnccryptedPostBytes() throws Exception
    {
        byte [] postBytes = super.buildMessageBytes();
        byte [] c = AES.encrypt(encryptionKey.getKey(), postBytes, post.getMessageID());
        return c;
    }

    private Messages.EncryptionKeyID buildEncryptionKeyID()
    {
        Messages.EncryptionKeyID encryptionKeyID = Messages.EncryptionKeyID.newBuilder()
                .setSenderID(encryptionKey.getKeyID().getSenderID().toLong())
                .setEncryptionKeyType(encryptionKey.getKeyID().getKeyType())
                .build();
        return encryptionKeyID;
    }
}
