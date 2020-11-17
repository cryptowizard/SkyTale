package world.database.Tables;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

import world.database.ChatHandler;
import world.database.ItemNotFoundException;
import world.skytale.cyphers.AES;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.ChatImp;


public class ChatHandlerTest extends DatabaseHandlerTest {


    private final ChatHandler chatHandler;

    public ChatHandlerTest()
    {
        this.chatHandler = getDatabaseHandler().getChatHandler();
    }


    public static ChatImp makeNewChat()
    {
        String name = "Hello";
        String imagePath = "bum";
        SecretKey conversationKey = AES.generateNewKey();
        ID chatID = ID.generateRandomID();
        Contact participant1 = TableContactsTest.makeNewContact();
        Contact participant2 = TableContactsTest.makeNewContact();


        //  chat. = imagePath;
        ID [] participants= new ID[]{participant1.getID(),participant2.getID()};

        ChatImp chat = new ChatImp(chatID,participants,name,conversationKey, null);


        return chat;
    }


    @Test
    public void addData() {

        ChatImp chat = makeNewChat();
        boolean result = chatHandler.addChat(chat);

        Assert.assertTrue(result);
    }

    @Test
    public void updateChat() throws ItemNotFoundException {

        ChatImp chat = makeNewChat();
        chatHandler.addChat(chat);

        chat.chatName = "XDDDD";
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
