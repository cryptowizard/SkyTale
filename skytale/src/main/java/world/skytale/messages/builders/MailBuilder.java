package world.skytale.messages.builders;

import java.io.IOException;

import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.MessageSignature;
import world.skytale.model.Account;
import world.skytale.model.Attachment;
import world.skytale.model.AttachmentFactory;


public abstract class MailBuilder {

    public static final String SIGNATURE_EXTENSION = "signature";
    public static final String MESSAGE_EXTENSION = "message";
    public static final String PUBLIC_KEY_EXTENSION = "pub";


    private final AttachmentFactory attachmentFactory;
    private final Account account;



    protected abstract void checkIfMessageIsSet() throws Exception;
    protected  abstract MessageHeader buildMessageHeader();
    protected abstract byte [] buildMessageBytes() throws Exception;

    public MailBuilder(AttachmentFactory attachmentFactory, Account account) {
        this.attachmentFactory = attachmentFactory;
        this.account = account;
    }


    public DownloadedMail makeDownloadedMail() throws Exception {

        checkIfMessageIsSet();
        MessageHeader messageHeader = buildMessageHeader();
        byte [] messageBytes = buildMessageBytes();

        return makeDownloadedMail(messageHeader, messageBytes);
    }
//    protected MessageHeader buildMessageHeader(Sendable sendable, ) {
//        return new MessageHeader(getTypeTag(), message.getSenderID(), message.getTime());
//    }


    private DownloadedMail makeDownloadedMail(MessageHeader messageHeader, byte [] messageBytes) throws IOException {

        String typeTag= messageHeader.getMessageType();

        if(typeTag.equals(FriendRequestResponseBuilder.TYPE_TAG)||typeTag.equals(FriendRequestBuilder.TYPE_TAG))
        {
            return buildSignedMailWithPublicKey(messageHeader, messageBytes);
        }
        return buildSignedMessage(messageHeader,messageBytes);

    }

    private  DownloadedMail buildSignedMessage(MessageHeader messageHeader, byte [] messageBytes) throws IOException {
        String title = messageHeader.makeTitle();
        byte [] signatureBytes = MessageSignature.singMessageWithHeader(messageHeader ,messageBytes,account.getPrivateKey());

        Attachment message = attachmentFactory.makeAttachment(MESSAGE_EXTENSION, messageBytes);
        Attachment signature = attachmentFactory.makeAttachment(SIGNATURE_EXTENSION, signatureBytes);

        DownloadedMail downloadedMail = new DownloadedMail();
        downloadedMail.setTitle(title);
        downloadedMail.addAttachment(message);
        downloadedMail.addAttachment(signature);

        return downloadedMail;
    }

    private DownloadedMail buildSignedMailWithPublicKey(MessageHeader messageHeader, byte [] messageBytes) throws IOException {
        DownloadedMail downloadedMail = buildSignedMessage(messageHeader,messageBytes);
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
