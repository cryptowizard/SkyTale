package world.xfreemedia;

import android.content.Context;

import java.util.Random;

import world.skytale.IncomingMessageProcessor;
import world.skytale.database.AccountProvider;
import world.skytale.database.DatabaseHandler;
import world.skytale.database.MailTransporter;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;
import world.skytale.model2.Account;

public class MockedUser implements MailTransporter, AccountProvider  {


    public Account account;
    public SkyTaleDatabaseHandler skyTaleDatabaseHandler;
    MockedNetwork mockedNetwork;
    IncomingMessageProcessor incomingMessageProcessor;

    public MockedUser (MockedNetwork mockedNetwork, String email, Context context, String picturePath)
    {
        this.mockedNetwork = mockedNetwork;
        account = UserAccount.makeNewAccount(email);
        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(context,account.getUserContact().getID().toString());
        SkyTaleDatabaseHandler skyTaleDatabaseHandler = new SkyTaleDatabaseHandler(sqlDatabaseHelper, this);


        this.skyTaleDatabaseHandler = skyTaleDatabaseHandler;
        this.incomingMessageProcessor = new IncomingMessageProcessor() {
            @Override
            protected DatabaseHandler getDatabaseHandler() {
                return skyTaleDatabaseHandler;
            }
        };





    }

    private static String randomName()
    {
        return "Name"+ new Random().nextInt();
    }




    public void receiveMail(IncomingMail incomingMail)
    {

    }


    public String getEmail()
    {
        return account.getUserContact().getAdress();
    }
    public String getExternalStorageDirectory()
    {
        return "/+user"+account.getUserContact().getID().toLong()+"/";
    }


    @Override
    public boolean sendMail(DownloadedMail downloadedMail, String recipents) {
        return mockedNetwork.sendMail(downloadedMail,recipents,getEmail());
    }

    @Override
    public Account getCurrentAccount() {
        return this.account;
    }
}
