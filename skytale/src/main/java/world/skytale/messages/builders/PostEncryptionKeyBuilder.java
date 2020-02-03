package world.skytale.messages.builders;

import java.util.Date;
import java.util.ArrayList;

import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Contact;
import world.skytale.model.sendable.EncryptionKey;

public class PostEncryptionKeyBuilder extends MailBuilder{

    public static final String TYPE_TAG = "ENCRYPTION_KEY";

    private ArrayList<EncryptionKey> encryptionKeys;
    private Contact reciversContact;


    public PostEncryptionKeyBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
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
        return new byte[0];
    }
}
