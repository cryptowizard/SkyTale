package world.skytale.messages;

import java.io.IOException;
import java.util.Collection;

import world.skytale.database.FilesHandler;
import world.skytale.MessageProcessingException;


/**
 * Every SkyTaleMessage is used to identify and load to object elements that are required in every SkyTaleMessage
 *
 */
public class SkyTaleMessage {

    public static final String SIGNATURE_EXTENSION = "signature";
    public static final String MESSAGE_EXTENSION = "message";

    private final MessageHeader messageHeader;
    private final byte [] signatureBytes;
    private final byte [] messageBytes;


    public SkyTaleMessage(MessageHeader messageHeader, byte [] messageBytes, byte [] signatureBytes)
    {
        this.messageHeader = messageHeader;
        this.messageBytes = messageBytes;
        this.signatureBytes = signatureBytes;
    }

    public SkyTaleMessage(DownloadedMail downloadedMail, FilesHandler filesHandler) throws MessageProcessingException, IOException {

        messageHeader = MessageHeader.parseTitle(downloadedMail.getTitle());

        String signaturePath = findPathWithExtension(SIGNATURE_EXTENSION, downloadedMail.getAttachmentPaths());
        String messagePath = findPathWithExtension(MESSAGE_EXTENSION, downloadedMail.getAttachmentPaths());


        this.signatureBytes = filesHandler.readFileBytes(signaturePath);
        this.messageBytes = filesHandler.readFileBytes(messagePath);
    }

    protected String findPathWithExtension(String fileExtension, Collection<String> paths) throws MessageProcessingException { ;
        for(String path : paths)
        {
            if(path.toLowerCase().contains(fileExtension.toLowerCase()))
            {
                return path;
            }
        }
        throw new MessageProcessingException("Message processing failed : required file is missing");
    }

    public DownloadedMail makeDownloadedMail(FilesHandler fileStore) throws IOException {
        String title = messageHeader.makeTitle();

        String signaturePath = fileStore.writeTemporaryFile(signatureBytes, SIGNATURE_EXTENSION);
        String messagePath = fileStore.writeTemporaryFile(messageBytes, MESSAGE_EXTENSION);


        DownloadedMail downloadedMail = new DownloadedMail();

        downloadedMail.setTitle(title);
        downloadedMail.addAttachment(signaturePath);
        downloadedMail.addAttachment(messagePath);
        return downloadedMail;
    }


    public byte[] getSignatureBytes() {
        return signatureBytes;
    }



    public byte[] getMessageBytes() {
        return messageBytes;
    }


    public MessageHeader getMessageHeader() {
        return messageHeader;
    }




}
