package world.skytale.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.database.ContactsHandler;
import world.skytale.databases.Tables.TableChat;
import world.skytale.databases.Tables.TableChatList;
import world.skytale.databases.Tables.TableContacts;
import world.skytale.databases.Tables.TableEncryptionKeys;
import world.skytale.databases.Tables.TablePosts;
import world.skytale.databases.daos.ChatDAO;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.Chat;
import world.skytale.model.AvaiableMessages.ChatMessage;
import world.skytale.model.ID;


public class SQLDatabaseHelper extends SQLiteOpenHelper implements  ChatHandler, ChatMessageHandler {

    public static final String DATABASE_NAME = "SkyTale.db";
    public static final int VERSION = 6;

    FilesHandlerImpl filesHandler;




    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public SQLDatabaseHelper(@Nullable Context context, String name) {
        super(context, name+DATABASE_NAME, null, VERSION);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TableContacts.CreateTable());
        db.execSQL(TableChatList.createTable());
        db.execSQL(TablePosts.createTable());
        db.execSQL(TableEncryptionKeys.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            clearAll(db);
    }

    public void clearAll(SQLiteDatabase db)
    {

        db.execSQL("DROP TABLE IF EXISTS " + TableContacts.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableChatList.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TablePosts.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableEncryptionKeys.TABLE_NAME);
        onCreate(db);
    }

    public ContactsHandler getTableContacts()
    {
        return new TableContacts(this);
    }

//    @Override
//    public ContactDAO getContact(ID contactID) throws ContactNotFoundException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContactDAO tmp =  TableContacts.getContact(db,contactID.toLong());
//        this.close();
//        return tmp;
//    }
//
//    @Override
//    public boolean addContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        boolean result=  TableContacts.addData(db,contact);
//        this.close();
//        return result;
//    }
//
//    @Override
//    public boolean updateContact(ContactImp contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int tmp=  TableContacts.updateContact(db,contact);
//        boolean result = (tmp>0);
//        this.close();
//        return result;
//    }
//
//    @Override
//    public boolean changeContactType(ID contactID, int contactType) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int tmp=  TableContacts.setContactType(db,contactID.toLong(),contactType);
//        boolean result = (tmp>0);
//        this.close();
//        return result;
//    }

    @Override
    public ChatDAO getChat(ID chatID) throws ChatNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        ChatDAO tmp =  TableChatList.getChat(db,chatID.toLong());
        this.close();
        return tmp;
    }

    @Override
    public boolean addChat(Chat chat) {



        ChatDAO chatDAO = ChatDAO.convertChatToDAO(chat,filesHandler);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChatList.addData(db,chatDAO);
        if( result)
        {
            db.execSQL(TableChat.createTable(chat.getChatID()));
            return true;
        }
        return false;
    }





    @Override
    public boolean updateChat(Chat chat) {

        ChatDAO chatDAO = ChatDAO.convertChatToDAO(chat,filesHandler);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChatList.updateChat(db,chatDAO);
        return result;
    }


    @Override
    public boolean addChatMessage(ChatMessage chatMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChat.addData(db, new ChatMessageDAO(chatMessage),chatMessage.getChatID());
        this.close();
        return result;
    }

    public ArrayList<ChatMessageDAO> getAllMessages(ID chatID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return  TableChat.selectAll(db,chatID);
    }



}
