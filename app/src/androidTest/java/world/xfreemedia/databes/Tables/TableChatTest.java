package world.xfreemedia.databes.Tables;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import world.skytale.model.Chat;
import world.xfreemedia.databes.SQLDatabaseHelper;
import world.xfreemedia.databes.TableChatListTest;

public class TableChatTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
   SQLDatabaseHelper sqlDatabaseHelper= new SQLDatabaseHelper(context);


   
    @Test
    public void addData() {

     Chat chat =    TableChatListTest.makeNewChat();
     sqlDatabaseHelper.addChat(chat);



    }


}