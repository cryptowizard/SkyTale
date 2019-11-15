package world.skytale;

import world.skytale.messages.DownloadedMail;
import world.skytale.model.Account;
import world.skytale.model.Contact;
import world.skytale.model.attachments.AttachmentFactory;


public class MailBuilder {

    public static final String SIGNATURE_EXTENSION = "signature";
    public static final String MESSAGE_EXTENSION = "message";
    public static final String PUBLIC_KEY_EXTENSION = "pub";


    private final AttachmentFactory attachmentFactory;
    private final Account account;

    public MailBuilder(AttachmentFactory attachmentFactory, Account account) {
        this.attachmentFactory = attachmentFactory;
        this.account = account;
    }


    public DownloadedMail makeDownloadedMail(VeryfiedMessage veryfiedMessage)
    {
        if(veryfiedMessage.getContactType()<Contact.TYPE_FOLLOWED)
        {
            return buildSignedMessage(veryfiedMessage);
        }
        else
        {
            return buildSignedMailWithPublicKey(veryfiedMessage);
        }
    }

    private  DownloadedMail buildSignedMessage(VeryfiedMessage veryfiedMessage)
    {
        return null;
    }

    private DownloadedMail buildSignedMailWithPublicKey(VeryfiedMessage veryfiedMessage)
    {
        return null;
    }




}
