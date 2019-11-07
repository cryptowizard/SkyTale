package world.xfreemedia.databes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import world.skytale.database.ContactsHandler;
import world.skytale.model.Contact;

public class SQLDatabaseHelper extends SQLiteOpenHelper implements ContactsHandler {

    public static final String DATABASE_NAME = "TestSQL.db";
    public static final int VERSION = 1;


    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableContacts.CreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Contact getContact(long contactID) throws ContactNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        Contact tmp =  TableContacts.getContact(db,contactID);
        this.close();
        return tmp;
    }

    @Override
    public boolean addContact(Contact contact) {
        return false;
    }

    @Override
    public boolean updateContact(Contact contact) {
        return false;
    }

    @Override
    public boolean changeContactType(String contactID, int contactType) {
        return false;
    }
}
