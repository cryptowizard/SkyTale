package world.skytale.messages.senders;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import world.skytale.database.MailTransporter;
import world.skytale.messages.DownloadedMail;

/**
 *  Each new message in order to be send
 */
public abstract class MessageSender {



    protected abstract boolean addToDatabase();
    protected abstract boolean removeFromDatabase();
    protected abstract DownloadedMail buildDownloadedMail() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException;
    protected abstract String getRecipients();

    private MailTransporter mailTransporter;

    public boolean send()
    {
        boolean addedToDatabaseSuccessfully = this.addToDatabase();
        if(!addedToDatabaseSuccessfully)
        {
            return false;
        }
        DownloadedMail downloadedMail;
        try {
            downloadedMail = buildDownloadedMail();
        }
        catch (Exception e)
        {
            return false;
        }
        String recipients = getRecipients();

        boolean sendSuccessfully = mailTransporter.sendMail(downloadedMail,recipients);

        if(!sendSuccessfully)
        {
            removeFromDatabase();
        }
        return false;
    }


}
