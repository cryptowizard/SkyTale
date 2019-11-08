package world.xfreemedia.databes;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.skytale.database.ChatHandler;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static world.xfreemedia.databes.TableContactsTest.makeNewContact;

public class TableChatListTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
    ChatHandler chatHandler = new SQLDatabaseHelper(context);



    public static Chat makeNewChat()
    {
        String name = "Hello";
        String imagePath = "bum";
        SecretKey conversationKey = AES.generateNewKey();
        ID chatID = ID.generateRandomID();
        Contact participant1 = makeNewContact();
        Contact participant2 = makeNewContact();
        Chat chat = new Chat();

        chat.chatImagePath = imagePath;
        chat.participantIDs = new ID[]{participant1.contactID,participant2.contactID};
        chat.secretKey = conversationKey;
        chat.chatName = name;
        chat.chatID = chatID;

        return chat;
    }

    @Test
    public void addData() {

        Chat chat = makeNewChat();
        boolean result = chatHandler.addChat(chat);

        assertTrue(result);
    }

    @Test
    public void updateChat() throws ChatHandler.ChatNotFoundExceptoin {

        Chat chat = makeNewChat();
        chatHandler.addChat(chat);

        chat.chatName = "XDDDD";
        chatHandler.updateChat(chat);
        Chat chat2 = chatHandler.getChat(chat.chatID);
        assertEquals(chat2.chatName,chat.chatName);
    }

    @Test
    public void getChat() throws ChatHandler.ChatNotFoundExceptoin {

        Chat chat = makeNewChat();
        chatHandler.addChat(chat);



        Chat chat2 = chatHandler.getChat(chat.chatID);
        assertEquals(chat2.chatName,chat.chatName);
        assertEquals(chat2.secretKey,chat.secretKey);
    }
}