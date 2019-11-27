package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import world.skytale.converters.PublickKeyConverter;
import world.skytale.database.ContactsHandler;
import world.skytale.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class TableContacts extends Table<ContactDAO, ID> implements ContactsHandler {



        public static final String TABLE_NAME = "Contacts";

        public static final String ID = "ID";
        public static final String email = "email";
        public static final String type = "contactType";
        public static final String publickKey = "publicKey";

    public TableContacts(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }


    public static String CreateTable() {
            String createTableContacts = "CREATE TABLE " + TableContacts.TABLE_NAME + " (\r\n" +
                    "	" + TableContacts.ID + "	INTEGER NOT NULL,\r\n" +
                    "	" + TableContacts.email + "	TEXT NOT NULL,\r\n" +
                    "	" + TableContacts.type + "	INTEGER DEFAULT 1,\r\n" +
                    "	" + TableContacts.publickKey + "	TEXT NOT NULL,\r\n" +
                    "	PRIMARY KEY(" + TableContacts.ID + ")\r\n" +
                    ");";
            return createTableContacts;
        }

    @Override
    public Contact getContact(world.skytale.model2.ID contactID) throws ItemNotFoundException {
        return this.getData(contactID);
    }

    @Override
    public boolean addContact(Contact contact) {
        return this.addData(makeDAO(contact));
    }

    @Override
    public boolean changeContactEmail(world.skytale.model2.ID contactID, String newAddress) {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableContacts.email, newAddress);
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, getWhereCondition(contactID) + "", null);
        sqlDatabaseHelper.close();
        if(result>0)
        {
            return true;
        }
        return false;
    }

    public ContactDAO makeDAO(Contact contact)
    {
        ContactDAO contactDAO = new ContactDAO(contact.getID(),contact.getPublicKey(),contact.getAdress(),contact.getContactType());
        return contactDAO;
    }


    @Override
    public boolean changeContactType(world.skytale.model2.ID contactID, int contactType) {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableContacts.type, contactType);
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, getWhereCondition(contactID) + "", null);
        sqlDatabaseHelper.close();
        if(result>0)
        {
            return true;
        }
        return false;
    }
    @Override
       protected ContactDAO readFromCursor(Cursor cursor) {
            String email, pub;
            int type = 0;
            PublicKey publickKey = null;
            long ID;


            ID = cursor.getLong(cursor.getColumnIndex(TableContacts.ID));
            email = cursor.getString(cursor.getColumnIndex(TableContacts.email));
            pub = cursor.getString(cursor.getColumnIndex(TableContacts.publickKey));
            type = cursor.getInt(cursor.getColumnIndex(TableContacts.type));
            try {
                publickKey = PublickKeyConverter.fromString(pub);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException("Should never happen " + e.getMessage());
            }


            ContactDAO contact = new ContactDAO(new ID(ID), publickKey, email,type);
            return contact;

        }

    @Override
    protected ContentValues putIntoContentValues(ContactDAO contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, contact.getID().toLong());
        contentValues.put(email, contact.getAdress());
        contentValues.put(publickKey, PublickKeyConverter.toString(contact.getPublicKey()));
        contentValues.put(type, contact.getContactType());
        return contentValues;
    }



    @Override
    protected String getTableName() {
        return TableContacts.TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(world.skytale.model2.ID id) {
        String condition = ID +" = "+id.toLong();
        return condition;
    }





}