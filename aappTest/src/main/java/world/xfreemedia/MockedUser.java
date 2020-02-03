package world.xfreemedia;

import android.content.Context;

import java.util.Random;

import world.skytale.MessageProcessingException;
import world.skytale.MessagesHandler;
import world.skytale.OutgoingMessageProcessor;
import world.database.AccountProvider;
import world.database.MailTransporter;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;
import world.skytale.model.Account;
import world.skytale.model.ID;
import world.skytale.model.implementations.LoadedAttachment;

public class MockedUser implements MailTransporter, AccountProvider  {



    public SkyTaleDatabaseHandler skyTaleDatabaseHandler;
    MailTransporter mockedNetwork;

    MessagesHandler incomingMessageProcessor;
 public    OutgoingMessageProcessor outgoinMessageProcessor;

    public MockedUser (MailTransporter mockedNetwork, String email, Context context)
    {
        this.mockedNetwork = mockedNetwork;
        Account account = UserAccount.makeNewAccount(email);
        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(context,account.getUserContact().getID().toString());
        SkyTaleDatabaseHandler skyTaleDatabaseHandler = new SkyTaleDatabaseHandler(sqlDatabaseHelper, account, context);
        outgoinMessageProcessor = new OutgoingMessageProcessor(this,skyTaleDatabaseHandler,new LoadedAttachment.LoadedAttachmentFactory());

        this.skyTaleDatabaseHandler = skyTaleDatabaseHandler;
        this.incomingMessageProcessor = new MessagesHandler(skyTaleDatabaseHandler);
    }

    private static String randomName()
    {
        return "Name"+ new Random().nextInt();
    }




    public void receiveMail(IncomingMail incomingMail)  {

        try {
            incomingMessageProcessor.processIncoming(incomingMail);
        }catch (MessageProcessingException exception)
        {
            exception.printStackTrace();
        }
    }


    public String getEmail()
    {
        return skyTaleDatabaseHandler.getAccountProvider().getCurrentAccount().getUserContact().getAdress();
    }
    public String getExternalStorageDirectory()
    {
        return "/+user"+skyTaleDatabaseHandler.getAccountProvider().getCurrentAccount().getUserContact().getID().toLong()+"/";
    }


    @Override
    public boolean sendMail(DownloadedMail downloadedMail, String recipents) throws MessageProcessingException {

            return mockedNetwork.sendMail(downloadedMail,recipents);

    }

    @Override
    public Account getCurrentAccount() {
        return skyTaleDatabaseHandler.getAccountProvider().getCurrentAccount();
    }

    public ID getUserID()
    {
        return getCurrentAccount().getUserContact().getID();
    }
}
