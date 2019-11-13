package world.skytale.databases.databes.Tables;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;

import world.skytale.model.Chat;
import world.skytale.model.ChatMessage;
import world.skytale.model.Contact;
import world.skytale.databases.databes.SQLDatabaseHelper;
import world.skytale.databases.databes.TableChatListTest;
import world.skytale.databases.databes.daos.ChatMessageDAO;

import static org.junit.Assert.assertEquals;
import static world.skytale.databases.databes.TableContactsTest.makeNewContact;

public class TableChatTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
   SQLDatabaseHelper sqlDatabaseHelper= new SQLDatabaseHelper(context);



    @Test
    public void addData() {

            Chat chat = TableChatListTest.makeNewChat();
            Log.i("chatID", "Hello :  " + chat.chatID);
            sqlDatabaseHelper.addChat(chat);


            Contact contact = makeNewContact();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.senderID = contact.contactID;
            chatMessage.message = "Hello";
            chatMessage.time = 100;
            chatMessage.attachments = new String[0];


            sqlDatabaseHelper.addChatMessage(chatMessage, chat.chatID);


            ArrayList<ChatMessageDAO> chatMessage2 = sqlDatabaseHelper.getAllMessages(chat.chatID);

            assertEquals(chatMessage2.size(), 1);
            assertEquals(chatMessage2.get(0).message, chatMessage.message);
        }


}