package world.skytale.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.database.ContactsHandler;
import world.skytale.database.FilesHandler;
import world.skytale.model.ChatImp;
import world.skytale.model.ChatMessageImp;
import world.skytale.model.ContactImp;
import world.skytale.model2.ID;
import world.skytale.databases.Tables.TableChat;
import world.skytale.databases.Tables.TableChatList;
import world.skytale.databases.Tables.TableContacts;
import world.skytale.databases.daos.ChatDAO;
import world.skytale.databases.daos.ChatMessageDAO;


public class SQLDatabaseHelper extends SQLiteOpenHelper implements ContactsHandler, ChatHandler, ChatMessageHandler {

    public static final String DATABASE_NAME = "SkyTale.db";
    public static final int VERSION = 3;

    FilesHandler filesHandler;



    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            clearAll(db);
    }

    public void clearAll(SQLiteDatabase db)
    {

        db.execSQL("DROP TABLE IF EXISTS " + TableContacts.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableChatList.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public ContactImp getContact(ID contactID) throws ContactNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContactImp tmp =  TableContacts.getContact(db,contactID.toLong());
        this.close();
        return tmp;
    }

    @Override
    public boolean addContact(ContactImp contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableContacts.addData(db,contact);
        this.close();
        return result;
    }

    @Override
    public boolean updateContact(ContactImp contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        int tmp=  TableContacts.updateContact(db,contact);
        boolean result = (tmp>0);
        this.close();
        return result;
    }

    @Override
    public boolean changeContactType(ID contactID, int contactType) {
        SQLiteDatabase db = this.getWritableDatabase();
        int tmp=  TableContacts.setContactType(db,contactID.toLong(),contactType);
        boolean result = (tmp>0);
        this.close();
        return result;
    }

    @Override
    public ChatImp getChat(ID chatID) throws ChatNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        ChatImp tmp =  TableChatList.getChat(db,chatID.toLong());
        this.close();
        return tmp;
    }

    @Override
    public boolean addChat(ChatImp chat) {

        ChatDAO chatDAO = new ChatDAO(chat);
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
    public boolean updateChat(ChatImp chat) {
        ChatDAO chatDAO = new ChatDAO(chat,filesHandler);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChatList.updateChat(db,chatDAO);
        return result;
    }

    @Override
    public boolean addChatMessage(ChatMessageImp chatMessage, ID chatID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChat.addData(db, new ChatMessageDAO(chatMessage),chatID);
        this.close();
        return result;
    }
    public ArrayList<ChatMessageDAO> getAllMessages(ID chatID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return  TableChat.selectAll(db,chatID);
    }



}
