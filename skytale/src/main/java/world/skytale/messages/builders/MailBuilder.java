package world.skytale.messages.builders;

import java.io.IOException;

import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageSignature;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model2.Account;
import world.skytale.model2.Attachment;
import world.skytale.model2.AttachmentFactory;
import world.skytale.model2.Contact;


public class MailBuilder {

    public static final String SIGNATURE_EXTENSION = "signature";
    public static final String MESSAGE_EXTENSION = "message";
    public static final String PUBLIC_KEY_EXTENSION = "pub";



    private final AttachmentFactory attachmentFactory;
    private final Account account;

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     * @param account user account with who's private key message will be signed
     */
    public MailBuilder(AttachmentFactory attachmentFactory, Account account) {
        this.attachmentFactory = attachmentFactory;
        this.account = account;
    }


    public DownloadedMail makeDownloadedMail(VeryfiedMessage veryfiedMessage) throws IOException {
        if(veryfiedMessage.getContactType()< Contact.TYPE_FOLLOWED)
        {
            return buildSignedMessage(veryfiedMessage);
        }
        else
        {
            return buildSignedMailWithPublicKey(veryfiedMessage);
        }
    }

    private  DownloadedMail buildSignedMessage(VeryfiedMessage veryfiedMessage) throws IOException {
        String title = veryfiedMessage.getMessageHeader().makeTitle();
        byte [] signatureBytes = MessageSignature.singMessageWithHeader(veryfiedMessage.getMessageHeader(),veryfiedMessage.getMessageBytes(),account.getPrivateKey());

        Attachment message = attachmentFactory.makeAttachment(MESSAGE_EXTENSION, veryfiedMessage.getMessageBytes());
        Attachment signature = attachmentFactory.makeAttachment(SIGNATURE_EXTENSION, signatureBytes);

        DownloadedMail downloadedMail = new DownloadedMail();
        downloadedMail.setTitle(title);
        downloadedMail.addAttachment(message);
        downloadedMail.addAttachment(signature);

        return downloadedMail;
    }

    private DownloadedMail buildSignedMailWithPublicKey(VeryfiedMessage veryfiedMessage) throws IOException {
        DownloadedMail downloadedMail = buildSignedMessage(veryfiedMessage);
        byte [] publicKeyBytes = account.getUserContact().getPublicKey().getEncoded();
        Attachment publicKey = attachmentFactory.makeAttachment(PUBLIC_KEY_EXTENSION, publicKeyBytes);
        downloadedMail.addAttachment(publicKey);
        return downloadedMail;
    }

   protected AttachmentFactory getAttachmentFactory() {
        return attachmentFactory;
    }

    protected Account getAccount() {
        return account;
    }



}
