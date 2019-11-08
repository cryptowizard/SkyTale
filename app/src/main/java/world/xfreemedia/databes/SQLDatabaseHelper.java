package world.xfreemedia.databes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import world.skytale.database.ChatHandler;
import world.skytale.database.ContactsHandler;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.ID;
import world.xfreemedia.databes.Tables.TableChatList;
import world.xfreemedia.databes.Tables.TableContacts;
import world.xfreemedia.databes.daos.ChatDAO;

public class SQLDatabaseHelper extends SQLiteOpenHelper implements ContactsHandler, ChatHandler {

    public static final String DATABASE_NAME = "SkyTale.db";
    public static final int VERSION = 3;


    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
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
    public Contact getContact(ID contactID) throws ContactNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        Contact tmp =  TableContacts.getContact(db,contactID.toLong());
        this.close();
        return tmp;
    }

    @Override
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableContacts.addData(db,contact);
        this.close();
        return result;
    }

    @Override
    public boolean updateContact(Contact contact) {
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
    public Chat getChat(ID chatID) throws ChatNotFoundExceptoin {
        SQLiteDatabase db = this.getWritableDatabase();
        Chat tmp =  TableChatList.getChat(db,chatID.toLong());
        this.close();
        return tmp;
    }

    @Override
    public boolean addChat(Chat chat) {

        ChatDAO chatDAO = new ChatDAO(chat);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChatList.addData(db,chatDAO);
        return result;
    }

    @Override
    public boolean updateChat(Chat chat) {
        ChatDAO chatDAO = new ChatDAO(chat);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=  TableChatList.updateChat(db,chatDAO);
        return result;
    }
}
