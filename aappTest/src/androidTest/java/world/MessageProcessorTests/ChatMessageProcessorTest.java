package world.MessageProcessorTests;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.MessagesHandler;
import world.database.AccountProvider;
import world.database.DatabaseHandler;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.SkyTaleDatabaseHandler;
import world.skytale.databases.UserAccount;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.ID;
import world.skytale.model.implementations.ChatImp;
import world.skytale.model.implementations.ChatMessageImp;
import world.skytale.model.implementations.LoadedAttachment;
import world.skytale.model.sendable.EncryptionKey;

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

        @Override
        public boolean updatePostEncryptionKeys(EncryptionKey friendsPostEncryptionKey, EncryptionKey followersEncryptionKey) {
            return false;
        }
    };

    AccountProvider revicerAccountProvider = new AccountProvider() {
        @Override
        public Account getCurrentAccount() {
            return reciver;
        }

        @Override
        public boolean updatePostEncryptionKeys(EncryptionKey friendsPostEncryptionKey, EncryptionKey followersEncryptionKey) {
            return false;
        }
    };


    FilesHandlerImpl filesHandler;


    @Test
    public void test() throws Exception {

        filesHandler = FilesHandlerImpl.getInstance(context);
        Log.i("worked", "worked");

        DatabaseHandler sendersDatabaseHandler = new SkyTaleDatabaseHandler(sendersDatabase, (UserAccount) senderAccountProvider.getCurrentAccount(),context);
        DatabaseHandler reciverDatabaseHandler = new SkyTaleDatabaseHandler(reciversDatabase, (UserAccount) revicerAccountProvider.getCurrentAccount(),context);


        MessagesHandler sendersMessageProcessor = new MessagesHandler(sendersDatabaseHandler);
        MessagesHandler reciversMessageProcessor = new MessagesHandler(reciverDatabaseHandler);

        sendersDatabaseHandler.getContactsHandler().addContact(reciver.getUserContact());
        reciverDatabaseHandler.getContactsHandler().addContact(sender.getUserContact());

        for(int i=0;i<10;i++) {
            ChatImp chat = ChatImp.startNewChat("TestChat", new ID[]{sender.getUserContact().getID(), reciver.getUserContact().getID()});
            Log.i("chat ", "chatID : "+chat.getChatID());




            sendersDatabaseHandler.getChatHandler().addChat(chat);
            reciverDatabaseHandler.getChatHandler().addChat(chat);


            ChatMessageImp chatMessage = new ChatMessageImp(chat.chatID, sender.getUserContact().getID(), new Date().getTime());


            sendersDatabase.addChatMessage(chatMessage);
            ChatMessageBuilder sendersMailBuilder = new ChatMessageBuilder((AttachmentFactory) new LoadedAttachment.LoadedAttachmentFactory(),sender);
            DownloadedMail downloadedMail = sendersMailBuilder.setMessage(chat,chatMessage).makeDownloadedMail();


            IncomingMail incomingMail = new IncomingMail(downloadedMail.getTitle(),downloadedMail.getAttachments(),sender.getUserContact().getAdress());
            reciversMessageProcessor.processIncoming(incomingMail);

            ArrayList<ChatMessageDAO> reciverMessages = reciversDatabase.getAllMessages(chat.getChatID());


            assertEquals(1, reciverMessages.size());

            ChatMessageDAO message = reciverMessages.get(0);

            //assertEquals(chatMessage.getMessage(), message.getMessage());
            assertEquals(chatMessage.getMessageID().getTime(), message.getMessageID().getTime());
        }



    }






}
