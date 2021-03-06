package world.database.Tables;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.crypto.SecretKey;

import world.database.ChatHandler;
import world.database.ItemNotFoundException;
import world.skytale.cyphers.AES;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.ChatImp;

import static org.junit.Assert.assertTrue;
import static world.database.Tables.TableContactsTest.makeNewContact;


public class TableChatListTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    Context context  =  InstrumentationRegistry.getTargetContext();
    ChatHandler chatHandler = new SQLDatabaseHelper(context);




    public static ChatImp makeNewChat()
    {
        String name = "Hello";
        String imagePath = "bum";
        SecretKey conversationKey = AES.generateNewKey();
        ID chatID = ID.generateRandomID();
        Contact participant1 = makeNewContact();
        Contact participant2 = makeNewContact();


      //  chat. = imagePath;
        ID [] participants= new ID[]{participant1.getID(),participant2.getID()};

        ChatImp chat = new ChatImp(chatID,participants,name,conversationKey, null);


        return chat;
    }

    @Test
    public void addData() {

        ChatImp chat = makeNewChat();
        boolean result = chatHandler.addChat(chat);

        assertTrue(result);
    }

    @Test
    public void updateChat() throws ItemNotFoundException{

        ChatImp chat = makeNewChat();
        chatHandler.addChat(chat);

        chat.chatName = "FriendsChatExample1";
        chatHandler.updateChat(chat);
        Chat chat2 = chatHandler.getChat(chat.chatID);
        Assert.assertEquals(chat2.getChatName(),chat.chatName);
    }

    @Test
    public void getChat() throws ItemNotFoundException {

        ChatImp chat = makeNewChat();
        chatHandler.addChat(chat);



        Chat chat2 = chatHandler.getChat(chat.chatID);
        Assert.assertEquals(chat2.getChatName(),chat.chatName);
        Assert.assertEquals(chat2.getSecretKey(),chat.secretKey);
    }
}