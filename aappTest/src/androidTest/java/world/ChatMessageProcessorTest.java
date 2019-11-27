package world;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;

import world.skytale.MessageProcessingException;
import world.skytale.MessagesHandler;
import world.skytale.database.AccountProvider;
import world.skytale.database.DatabaseHandler;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model.ChatImp;
import world.skytale.model.ChatMessageImp;
import world.skytale.model.attachments.LoadedAttachment;
import world.skytale.model2.Account;
import world.skytale.model2.AttachmentFactory;
import world.skytale.model2.ChatMessage;
import world.skytale.model2.ID;

import static org.junit.Assert.assertEquals;

public class ChatMessageProcessorTest {



    Context context  =  InstrumentationRegistry.getTargetContext();

    Account sender = UserAccount.makeNewAccount("sender@gmail.com");
    Account reciver = UserAccount.makeNewAccount("reciver@gmail.com");
//
//    @Rule
//    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);


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


    FilesHandlerImpl filesHandler;


    @Test
    public void test() throws FilesHandlerImpl.StoragePermissionDeniedException, IOException, InvalidKeyException, MessageProcessingException, SignatureException, NoSuchAlgorithmException {

        filesHandler = FilesHandlerImpl.getInstance(context);
        Log.i("worked", "worked");

        DatabaseHandler sendersDatabaseHandler = new SkyTaleDatabaseHandler(sendersDatabase, senderAccountProvider);
        DatabaseHandler reciverDatabaseHandler = new SkyTaleDatabaseHandler(reciversDatabase, revicerAccountProvider);


        MessagesHandler sendersMessageProcessor = new MessagesHandler(sendersDatabaseHandler);
        MessagesHandler reciversMessageProcessor = new MessagesHandler(reciverDatabaseHandler);

        sendersDatabaseHandler.getTableContacts().addContact(reciver.getUserContact());
        reciverDatabaseHandler.getTableContacts().addContact(sender.getUserContact());

        for(int i=0;i<10;i++) {
            ChatImp chat = ChatImp.startNewChat("TestChat", new ID[]{sender.getUserContact().getID(), reciver.getUserContact().getID()});
            Log.i("chat ", "chatID : "+chat.getChatID());




            sendersDatabaseHandler.getChatHandler().addChat(chat);
            reciverDatabaseHandler.getChatHandler().addChat(chat);


            ChatMessageImp chatMessage = new ChatMessageImp(sender.getUserContact().getID(), new Date().getTime(), "Hello my new USer", null);


            sendersDatabase.addChatMessage(chatMessage, chat.getChatID());
            ChatMessageBuilder sendersMailBuilder = new ChatMessageBuilder((AttachmentFactory) new LoadedAttachment.LoadedAttachmentFactory(),sender);
            DownloadedMail downloadedMail = sendersMailBuilder.makeDownloadedMail(chatMessage,chat);


            IncomingMail incomingMail = new IncomingMail(downloadedMail.getTitle(),downloadedMail.getAttachments(),sender.getUserContact().getAdress());
            reciversMessageProcessor.processIncoming(incomingMail);

            ArrayList<ChatMessageDAO> reciverMessages = reciversDatabase.getAllMessages(chat.getChatID());


            assertEquals(1, reciverMessages.size());

            ChatMessage message = reciverMessages.get(0);

            assertEquals(chatMessage.getMessage(), message.getMessage());
            assertEquals(chatMessage.getTime(), message.getTime());
        }



    }






}
