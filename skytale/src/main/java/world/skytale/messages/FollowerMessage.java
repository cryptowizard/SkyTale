package world.skytale.messages;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.database.FilesHandler;
import world.skytale.messages.processors.MessageProcessingException;

public class FollowerMessage extends SkyTaleMessage {

    public static final String PUBLIC_KEY_EXTENSION = ".public";

    private PublicKey sendersPublicKey;

    public FollowerMessage(DownloadedMail downloadedMail, FilesHandler filesHandler) throws MessageProcessingException, IOException {
        super(downloadedMail, filesHandler);

        String publickKeyPath = findPathWithExtension(PUBLIC_KEY_EXTENSION, downloadedMail.getAttachmentPaths());


        byte [] publicKeyBytes = filesHandler.readFileBytes(new File(publickKeyPath));
        sendersPublicKey = AccountKey.fromBytes(publicKeyBytes);
    }

    public DownloadedMail makeDownloadedMail(FilesHandler fileStore)
    {
        DownloadedMail downloadedMail = super.makeDownloadedMail(fileStore);
        String publicKeyPath =  fileStore.writeTemporaryFile(sendersPublicKey.getEncoded(),PUBLIC_KEY_EXTENSION);
        downloadedMail.addAttachment(publicKeyPath);
        return downloadedMail;
    }
}
