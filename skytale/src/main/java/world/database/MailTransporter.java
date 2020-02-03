package world.database;

import world.skytale.MessageProcessingException;
import world.skytale.messages.DownloadedMail;

public interface MailTransporter {

    /**
     *
     * @param downloadedMail Message that should be delivered
     * @param recipents  "skytalemsd@gmail.com,mail2@gmail.com,mail3@outlook.com,msd@xfreemedia.com"
     * @return true if message was delivered and false if message delivery failed"
     */
    public boolean sendMail(DownloadedMail downloadedMail, String recipents) throws MessageProcessingException;
}
