package world.skytale.messages.senders;


import java.util.List;

import world.database.DatabaseHandler;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;

/**
 *  Each new message in order to be send
 */
public abstract class MessageSender {


    public MessageSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        this.mailTransporter = mailTransporter;
        this.databaseHandler = databaseHandler;
        this.account = databaseHandler.getAccountProvider().getCurrentAccount();
        this.attachmentFactory = attachmentFactory;
    }

    protected abstract boolean addToDatabase();

    /**
     * If sending fails message should be removed from database
     * @return
     */
    protected abstract boolean removeFromDatabase();
    protected abstract DownloadedMail buildDownloadedMail() throws Exception;
    protected abstract List<String> getRecipientAdresses();

    private final MailTransporter mailTransporter;
    protected final DatabaseHandler databaseHandler;
    protected  final AttachmentFactory attachmentFactory;
    protected final Account account;

    protected boolean send() throws Exception {
        boolean addedToDatabaseSuccessfully = this.addToDatabase();
        if(!addedToDatabaseSuccessfully)
        {
            return false;
        }
        DownloadedMail downloadedMail;

        try {
            downloadedMail = buildDownloadedMail();
        }catch (Exception e)
        {
            removeFromDatabase();
            return false;
        }
        String recipients = toString(getRecipientAdresses());


        boolean sendSuccessfully = mailTransporter.sendMail(downloadedMail,recipients);

        if(!sendSuccessfully)
        {
            removeFromDatabase();
            return false;
        }
        return true;
    }

    private String toString(List<String> addressList)
    {
        String tmp="";
        if(addressList==null||addressList.size()==0)
        {
            return tmp;
        }

        for(String adres: addressList)
        {
            tmp+=","+adres;
        }
        return tmp.substring(1);
    }


}
