package world.database.Tables;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.model.ChatMessageDAO;
import world.skytale.databases.model.DisplayableDAO;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ChatImp;

import static org.junit.Assert.assertEquals;
import static world.database.Tables.TableContactsTest.makeNewContact;

public class TableChatTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
   SQLDatabaseHelper sqlDatabaseHelper= new SQLDatabaseHelper(context);



    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Test
    public void addData() {

            ChatImp chat = TableChatListTest.makeNewChat();
            Log.i("chatID", "Hello :  " + chat.chatID);
            sqlDatabaseHelper.addChat(chat);


            Contact contact = makeNewContact();
            ChatMessageDAO chatMessage = new ChatMessageDAO(contact.getID(),new Date().getTime(),chat.chatID);

             DisplayableDAO displayable = new DisplayableDAO();
             displayable.setText("Hello this is my new text");

             chatMessage.setDisplayable(displayable);

            sqlDatabaseHelper.addChatMessage(chatMessage);


            ArrayList<ChatMessageDAO> chatMessage2 = sqlDatabaseHelper.getAllMessages(chat.chatID);

            assertEquals(chatMessage2.size(), 1);
            assertEquals(chatMessage2.get(0).getDisplayable().getText(), chatMessage.getDisplayable().getText());
        }





}