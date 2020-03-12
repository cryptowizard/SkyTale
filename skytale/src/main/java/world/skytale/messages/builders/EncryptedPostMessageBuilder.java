package world.skytale.messages.builders;

import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;

public class EncryptedPostMessageBuilder extends MailBuilder {

    public static final String TYPE_TAG = "ENC_POST";



    public EncryptedPostMessageBuilder(AttachmentFactory attachmentFactory, Account account)
    {
        super(attachmentFactory,account);
    }

    @Override
    protected void checkIfMessageIsSet() throws Exception {

    }

    @Override
    protected MessageHeader buildMessageHeader() {
        return null;
    }

    @Override
    protected byte[] buildMessageBytes() throws Exception {
        return new byte[0];
    }
}
