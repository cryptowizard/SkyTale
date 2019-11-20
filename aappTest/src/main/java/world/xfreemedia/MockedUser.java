package world.xfreemedia;

import android.content.Context;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Random;

import world.skytale.IncomingMessageProcessor;
import world.skytale.database.AccountProvider;
import world.skytale.database.DatabaseHandler;
import world.skytale.database.MailSender;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.senders.MessageSender;
import world.skytale.model2.Account;

public class MockedUser implements MailSender, AccountProvider  {


    public Account account;
    public SkyTaleDatabaseHandler skyTaleDatabaseHandler;
    MockedNetwork mockedNetwork;
    IncomingMessageProcessor incomingMessageProcessor;

    public MockedUser (MockedNetwork mockedNetwork, String email, Context context, String picturePath)
    {
        this.mockedNetwork = mockedNetwork;
        account = UserAccount.makeNewAccount(randomName(),randomName(),email,picturePath);
        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(context,account.getUserContact().getID().toString());
        SkyTaleDatabaseHandler skyTaleDatabaseHandler = new SkyTaleDatabaseHandler(sqlDatabaseHelper, this);

        MessageSender messageSender = new MessageSender() {
            @Override
            protected boolean addToDatabase() {
                return false;
            }

            @Override
            protected boolean removeFromDatabase() {
                return false;
            }

            @Override
            protected DownloadedMail buildDownloadedMail() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
                return null;
            }

            @Override
            protected String getRecipients() {
                return null;
            }
        }
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
