package world;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;

import world.skytale.database.AccountProvider;
import world.skytale.database.DatabaseHandler;
import world.skytale.database.FilesHandler;
import world.skytale.databases.FilesHandlerImpl;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.messages.DownloadedMail;
import world.skytale.MessageProcessingException;
import world.skytale.model2.Account;
import world.skytale.model.ChatImp;
import world.skytale.model.ChatMessageImp;
import world.skytale.model2.ID;

import static org.junit.Assert.assertEquals;

public class ChatMessageProcessorTest {

    Context context  =  InstrumentationRegistry.getTargetContext();

    Account sender = UserAccount.makeNewAccount("Maciej","Sender","sender@gmail.com","");
    Account reciver = UserAccount.makeNewAccount("Maciej","Sender","sender@gmail.com","");


    SQLDatabaseHelper sendersDatabase = new SQLDatabaseHelper(context,"sender");
    SQLDatabaseHelper reciversDatabase = new SQLDatabaseHelper(context,"reciver");

    AccountProvider senderAccountProvider = new AccountProvider() {
        @Override
        public Account getCurrentAccount() {
            return sender;
        }
    };

    AccountProvider revicerAccountProvider = new AccountProvider() {
        @Override
        public Account getCurrentAccount() {
            return reciver;
        }
    };


    FilesHandler filesHandler;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Test
    public void test() throws FilesHandlerImpl.StoragePermissionDeniedException, IOException, InvalidKeyException, MessageProcessingException, SignatureException, NoSuchAlgorithmException {

        filesHandler = FilesHandlerImpl.getInstance(context);
        Log.i("worked", "worked");

        DatabaseHandler sendersDatabaseHandler = new SkyTaleDatabaseHandler(sendersDatabase, senderAccountProvider);
        DatabaseHandler reciverDatabaseHandler = new SkyTaleDatabaseHandler(reciversDatabase, revicerAccountProvider);


        MessagesHandler sendersMessageProcessor = new MessagesHandler(sendersDatabaseHandler, filesHandler);
        MessagesHandler reciversMessageProcessor = new MessagesHandler(reciverDatabaseHandler, filesHandler);

        sendersDatabaseHandler.getTableContacts().addContact(reciver.getUserContact());
        reciverDatabaseHandler.getTableContacts().addContact(sender.getUserContact());

        for(int i=0;i<1000;i++) {
            ChatImp chat = ChatImp.startNewChat("TestChat", new ID[]{sender.getUserContact().contactID, reciver.getUserContact().contactID});
            Log.i("chat ", "chatID : "+chat.chatID);




            sendersDatabaseHandler.getChatHandler().addChat(chat);
            reciverDatabaseHandler.getChatHandler().addChat(chat);


            ChatMessageImp chatMessage = new ChatMessageImp(sender.getUserContact().contactID, new Date().getTime(), "Hello my new USer", null);


            sendersDatabase.addChatMessage(chatMessage, chat.chatID);
            DownloadedMail downloadedMail = sendersMessageProcessor.getChatMessageProcessor().makeDownloadedMail(chatMessage, chat);


            reciversMessageProcessor.processIncoming(downloadedMail);

            ArrayList<ChatMessageDAO> reciverMessages = reciversDatabase.getAllMessages(chat.chatID);


            assertEquals(1, reciverMessages.size());

            ChatMessageImp message = reciverMessages.get(0);

            assertEquals(chatMessage.message, message.message);
            assertEquals(chatMessage.time, message.time);
        }



    }






}
