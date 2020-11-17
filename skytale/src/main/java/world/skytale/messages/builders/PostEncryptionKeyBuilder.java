package world.skytale.messages.builders;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.ElipticCurveCypher;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Contact;
import world.skytale.model.proto.EncryptionKeyProto;
import world.skytale.model.EncryptionKey;

public class PostEncryptionKeyBuilder extends MailBuilder{

    public static final String TYPE_TAG = "ENCRYPTION_KEY";

    private ArrayList<EncryptionKey> encryptionKeys;
    private Contact reciversContact;

    public PostEncryptionKeyBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }

    public void setEncryptionKeys(ArrayList<EncryptionKey> encryptionKeys) {
        this.encryptionKeys = encryptionKeys;
    }

    public void setReciversContact(Contact reciversContact) {
        this.reciversContact = reciversContact;
    }

   public void setMessage(ArrayList<EncryptionKey> encryptionKeys, Contact reciversContact)
    {
        this.encryptionKeys = encryptionKeys;
        this.reciversContact = reciversContact;
    }


    @Override
    protected void checkIfMessageIsSet() throws Exception {
        if(reciversContact==null) throw new Exception("Post not set");
        if(encryptionKeys==null || encryptionKeys.size()==0) throw new Exception("xf");
    }

    @Override
    protected MessageHeader buildMessageHeader() {
        return  new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(),new Date().getTime());
    }

    @Override
    protected byte[] buildMessageBytes() throws Exception {

        Messages.PostEncryptionKeys.Builder builder = Messages.PostEncryptionKeys.newBuilder();

        for(EncryptionKey encryptionKey : encryptionKeys)
        {
            Messages.EncryptionKey protoKey = EncryptionKeyProto.toProtoMessage(encryptionKey);
            byte [] m = protoKey.toByteArray();
            byte [] c = encryptWithReceiverPublicKey(m);
            builder.addEncryptedEncryptionKeys(ByteConverter.toByteString(c));
        }

        return builder.build().toByteArray();
    }


    private byte [] encryptWithReceiverPublicKey(byte [] m)
    {
        return ElipticCurveCypher.encrypt(reciversContact.getPublicKey(),m);
    }
}
