package world.skytale.databases.databes.Tables;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;

import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.databes.TableChatListTest;
import world.skytale.model.Attachment;
import world.skytale.model.AvaiableMessages.ChatMessage;
import world.skytale.model.Contact;
import world.skytale.model.ID;
import world.skytale.proto.ChatImp;

import static org.junit.Assert.assertEquals;
import static world.skytale.databases.databes.TableContactsTest.makeNewContact;

public class TableChatTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
   SQLDatabaseHelper sqlDatabaseHelper= new SQLDatabaseHelper(context);



    @Test
    public void addData() {

            ChatImp chat = TableChatListTest.makeNewChat();
            Log.i("chatID", "Hello :  " + chat.chatID);
            sqlDatabaseHelper.addChat(chat);


            Contact contact = makeNewContact();
            ChatMessageDAO chatMessage = new ChatMessageDAO();
            chatMessage.senderID = contact.getID();
            chatMessage.message = "Hello";
            chatMessage.time = 100;
            chatMessage.attachments = new String[0];


            sqlDatabaseHelper.addChatMessage(new ChatMessageD(chatMessage, chat.chatID));


            ArrayList<ChatMessageDAO> chatMessage2 = sqlDatabaseHelper.getAllMessages(chat.chatID);

            assertEquals(chatMessage2.size(), 1);
            assertEquals(chatMessage2.get(0).message, chatMessage.message);
        }


        public static class ChatMessageD  implements ChatMessage
        {
            private final ID chatID;
            private final ChatMessageDAO dao;


            public ChatMessageD(ChatMessageDAO dao , ID chatID) {
                this.chatID = chatID;
                this.dao = dao;
            }

            public ID getChatID()
            {
                return chatID;
            }

            @NonNull
            @Override
            public ID getSenderID() {
                return dao.senderID;
            }

            @NonNull
            @Override
            public long getTime() {
                return dao.time;
            }

            @Override
            public String getMessage() {
                return dao.getMessage();
            }

            @Override
            public ArrayList<Attachment> getAttachments() {
                return dao.getAttachments();
            }
        }


}