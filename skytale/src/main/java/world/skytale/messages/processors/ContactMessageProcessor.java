package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import world.skytale.MessageProcessingException;
import world.skytale.cyphers.RSASignature;
import world.skytale.database.ContactsHandler;
import world.skytale.database.FilesHandler;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.MessagesHandler;
import world.skytale.messages.SkyTaleMessage;
import world.skytale.model.Account;
import world.skytale.model.Contact;


/**
 *
 */
public abstract class ContactMessageProcessor implements MessageProcessor {

    protected ContactsHandler contactsHandler;
    protected FilesHandler fileHandler;
    protected Account userAccount;

    public ContactMessageProcessor(MessagesHandler messagesHandler) {
        this.contactsHandler = messagesHandler.getDatabaseHandler().getTableContacts();
        this.fileHandler = messagesHandler.getFilesHandler();
        this.userAccount = messagesHandler.getDatabaseHandler().getAccountProvider().getCurrentAccount();
    }

    @Override
    public void processIncoming(DownloadedMail downloadedMail) throws MessageProcessingException, IOException {
        SkyTaleMessage skyTaleMessage = new SkyTaleMessage(downloadedMail, fileHandler);
        boolean checkSignature = verifySignature(skyTaleMessage);

        if(!checkSignature)
        {
            throw new MessageProcessingException("Wrong signature");
        }
        try {
            processIncoming(skyTaleMessage);
        }
        catch (Exception e)
        {
            throw new MessageProcessingException(e.getMessage());
        }
    }

    private boolean verifySignature(SkyTaleMessage skyTaleMessage) throws MessageProcessingException {
        try {
            Contact sender = contactsHandler.getContact(skyTaleMessage.getMessageHeader().getSenderID());
            return RSASignature.veryfiSignature(sender.publicKey, skyTaleMessage.getMessageBytes(), skyTaleMessage.getSignatureBytes());
        }catch (ContactsHandler.ContactNotFoundException exceptionn)
        {
            throw new MessageProcessingException(exceptionn.getMessage());
        }
    }

    public abstract void processIncoming(SkyTaleMessage skyTaleMessage) throws Exception;


    protected DownloadedMail makeDownloadedMail(MessageHeader messageHeader, byte [] messageBytes) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] signatureBytes = RSASignature.sign(userAccount.getPrivateKey(), messageBytes);
        SkyTaleMessage skyTaleMessage = new SkyTaleMessage(messageHeader, messageBytes, signatureBytes);
        DownloadedMail downloadedMail = skyTaleMessage.makeDownloadedMail(fileHandler);
        return downloadedMail;
    }
}
