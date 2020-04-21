package world.skytale.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import world.database.ChatHandler;
import world.database.ChatMessageHandler;
import world.database.ContactsHandler;
import world.database.ItemNotFoundException;
import world.database.Tables.ProfilePageTable;
import world.database.Tables.TableChat;
import world.database.Tables.TableChatList;
import world.database.Tables.TableContacts;
import world.database.Tables.TableEncryptionKeys;
import world.database.Tables.TableFriendRequest;
import world.database.Tables.TablePosts;
import world.skytale.databases.daos.ChatDAO;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.Chat;
import world.skytale.model.ID;
import world.skytale.model.sendable.ChatMessage;


public class SQLDatabaseHelper extends SQLiteOpenHelper implements  ChatHandler, ChatMessageHandler {

    public static final String DATABASE_NAME = "SkyTale.db";
    public static final int VERSION = 8;

    FilesHandlerImpl filesHandler;


    public FilesHandlerImpl getFilesHandler()
    {
        return filesHandler;
    }

    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }

    public SQLDatabaseHelper(@Nullable Context context, String name) {
        super(context, name+DATABASE_NAME, null, VERSION);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        dropAllTables(db);
        db.execSQL(TableContacts.CreateTable());
        db.execSQL(TableChatList.createTable());
        db.execSQL(TablePosts.createTable());
        db.execSQL(TableEncryptionKeys.createTable());
        db.execSQL(TableFriendRequest.createTable());
        db.execSQL(ProfilePageTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           dropAllTables(db);
            onCreate(db);
    }

    public void clearAll(SQLiteDatabase db)
    {
        dropAllTables(db);

//        db.execSQL("DROP TABLE IF EXISTS " + TableContacts.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TableChatList.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TablePosts.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TableEncryptionKeys.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS "+ TableFriendRequest.TABLE_NAME);
        onCreate(db);
    }

    public void dropAllTables(SQLiteDatabase db)
    {
        Cursor c = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type IS 'table'" +
                        " AND name NOT IN ('sqlite_master', 'sqlite_sequence')",
                null
        );
        if(c.moveToFirst()){
            do{
                db.execSQL("DROP TABLE " + c.getString(c.getColumnIndex("name")));
            }while(c.moveToNext());
        }
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
    public ChatDAO getChat(ID chatID) throws ItemNotFoundException {
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
    public boolean removeChat(Chat chat) {
        return false;
    }


    @Override
    public boolean addChatMessage(ChatMessage chatMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ChatMessageDAO dao = TableChat.toDAO(chatMessage,filesHandler);
        boolean result=  TableChat.addData(db, dao);
        this.close();
        return result;
    }

    @Override
    public boolean removeChatMessage(ChatMessage chatMessage) {
        return false;
    }

    public ArrayList<ChatMessageDAO> getAllMessages(ID chatID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return  TableChat.selectAll(db,chatID);
    }



}
