package world.skytale.messages.builders;

import java.io.IOException;

import world.skytale.database.DatabaseHandler;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.MessageSignature;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Account;
import world.skytale.model.Attachment;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.Sendable;


public abstract class MailBuilder<Message extends Sendable> {

    public static final String SIGNATURE_EXTENSION = "signature";
    public static final String MESSAGE_EXTENSION = "message";
    public static final String PUBLIC_KEY_EXTENSION = "pub";


    private final AttachmentFactory attachmentFactory;
    private final Account account;


    protected  abstract  String getTypeTag();
    protected abstract byte [] buildMessageBytes(Message message);

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     *
     */
    public MailBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        this.attachmentFactory = attachmentFactory;
        this.account = databaseHandler.getAccountProvider().getCurrentAccount();
    }

    public DownloadedMail makeDownloadedMail(Message message) throws IOException {
        MessageHeader messageHeader = buildMessageHeader(message);
        byte [] messageBytes = buildMessageBytes(message);

        VeryfiedMessage veryfiedMessage = new VeryfiedMessage(messageHeader, messageBytes);
        return makeDownloadedMail(veryfiedMessage);
    }
    private MessageHeader buildMessageHeader(Message message) {
        return new MessageHeader(getTypeTag(), message.getSenderID(), message.getTime());
    }


    private DownloadedMail makeDownloadedMail(VeryfiedMessage veryfiedMessage) throws IOException {

        String typeTag= veryfiedMessage.getMessageHeader().getMessageType();

        if(typeTag.equals(FriendRequestResponseBuilder.TYPE_TAG))
        {
            return buildSignedMailWithPublicKey(veryfiedMessage);
        }
        return buildSignedMessage(veryfiedMessage);

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
