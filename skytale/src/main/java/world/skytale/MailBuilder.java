package world.skytale;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import world.skytale.cyphers.RSASignature;
import world.skytale.messages.DownloadedMail;
import world.skytale.model2.Account;
import world.skytale.model.ContactImp;
import world.skytale.model2.Attachment;
import world.skytale.model2.AttachmentFactory;


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


    public DownloadedMail makeDownloadedMail(VeryfiedMessage veryfiedMessage) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        if(veryfiedMessage.getContactType()< ContactImp.TYPE_FOLLOWED)
        {
            return buildSignedMessage(veryfiedMessage);
        }
        else
        {
            return buildSignedMailWithPublicKey(veryfiedMessage);
        }
    }

    private  DownloadedMail buildSignedMessage(VeryfiedMessage veryfiedMessage) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        String title = veryfiedMessage.getMessageHeader().makeTitle();
        byte [] signatureBytes = RSASignature.sign(account.getPrivateKey() , veryfiedMessage.getMessageBytes());

        Attachment message = attachmentFactory.makeAttachment(MESSAGE_EXTENSION, veryfiedMessage.getMessageBytes());
        Attachment signature = attachmentFactory.makeAttachment(SIGNATURE_EXTENSION, signatureBytes);

        DownloadedMail downloadedMail = new DownloadedMail();
        downloadedMail.setTitle(title);
        downloadedMail.addAttachment(message);
        downloadedMail.addAttachment(signature);

        return downloadedMail;
    }

    private DownloadedMail buildSignedMailWithPublicKey(VeryfiedMessage veryfiedMessage) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
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
