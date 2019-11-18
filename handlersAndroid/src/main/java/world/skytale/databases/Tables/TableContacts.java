package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import world.skytale.converters.PublickKeyConverter;
import world.skytale.database.ContactsHandler;
import world.skytale.databases.FilesHandlerImpl;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class TableContacts implements ContactsHandler {



        public static final String TABLE_NAME = "Contacts";

        public static final String ID = "ID";
        public static final String firstName = "firstName";
        public static final String lastName = "lastName";
        public static final String email = "email";
        public static final String picturePath = "picturePath";
        public static final String type = "contactType";
        public static final String publickKey = "publicKey";


        private final SQLDatabaseHelper sqlDatabaseHelper;
        private final FilesHandlerImpl filesHandler;

        public TableContacts(SQLDatabaseHelper sqlDatabaseHelper, FilesHandlerImpl filesHandler) {
            this.sqlDatabaseHelper = sqlDatabaseHelper;
            this.filesHandler = filesHandler;
        }


    public static String CreateTable() {
            String createTableContacts = "CREATE TABLE " + TableContacts.TABLE_NAME + " (\r\n" +
                    "	" + TableContacts.ID + "	INTEGER NOT NULL,\r\n" +
                    "	" + TableContacts.firstName + "	TEXT,\r\n" +
                    "	" + TableContacts.lastName + "	TEXT,\r\n" +
                    "	" + TableContacts.email + "	TEXT NOT NULL,\r\n" +
                    "	" + TableContacts.picturePath + "	TEXT,\r\n" +
                    "	" + TableContacts.type + "	INTEGER DEFAULT 1,\r\n" +
                    "	" + TableContacts.publickKey + "	TEXT NOT NULL,\r\n" +
                    "	PRIMARY KEY(" + TableContacts.ID + ")\r\n" +
                    ");";
            return createTableContacts;
        }


    @Override
    public ContactDAO getContact(world.skytale.model2.ID contactID) throws ContactNotFoundException {
        long id = contactID.toLong();
        String query = "SELECT  * FROM " + TABLE_NAME +
                "\n WHERE " + ID + " = '" + id + "';";

        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.getCount() == 0) {
            throw new ContactsHandler.ContactNotFoundException(new ID(id));
        }
        cursor.moveToFirst();
        ContactDAO contact = readFromCursor(cursor);
        db.close();
        return contact;
    }

    @Override
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = putContactIntoContentValues(getContactDAO(contact));
        long result = db.insert(TABLE_NAME, null, contentValues);

        sqlDatabaseHelper.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean updateContact(Contact contact) {
        ContactDAO contactDAO = getContactDAO(contact);
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = putContactIntoContentValues(contactDAO);
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, TableContacts.ID + "= " + contact.getID().toLong() + "", null);

        db.close();
        if(result>0)
        {
            return true;
        }
        return false;

    }

    private ContactDAO getContactDAO(Contact contact)
    {
        String profilePicturePath = "";
        try {
             profilePicturePath = filesHandler.saveAttachment(contact.getProfilePicture());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContactDAO tmp = new ContactDAO(contact.getID(),contact.getPublicKey(),contact.getAdress(),contact.getFirstName(),contact.getLastName(),contact.getContactType(),profilePicturePath);
        return tmp;
    }






    @Override
    public boolean changeContactType(world.skytale.model2.ID contactID, int contactType) {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableContacts.type, type);
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, TableContacts.ID + " = " + ID + "", null);
        sqlDatabaseHelper.close();
        if(result>0)
        {
            return true;
        }
        return false;
    }


        private static ContentValues putContactIntoContentValues(ContactDAO contact) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, contact.getID().toLong());
            contentValues.put(firstName, contact.firstName);
            contentValues.put(lastName, contact.lastName);
            contentValues.put(email, contact.address);
            contentValues.put(picturePath, contact.profilePicture);
            contentValues.put(publickKey, PublickKeyConverter.toString(contact.publicKey));
            contentValues.put(type, contact.contactType);
            return contentValues;
        }






        private static ContactDAO readFromCursor(Cursor cursor) {
            String first, last, email, picture, pub;
            int type = 0;
            PublicKey publickKey = null;
            long ID;


            ID = cursor.getLong(cursor.getColumnIndex(TableContacts.ID));
            first = cursor.getString(cursor.getColumnIndex(TableContacts.firstName));
            last = cursor.getString(cursor.getColumnIndex(TableContacts.lastName));
            email = cursor.getString(cursor.getColumnIndex(TableContacts.email));
            picture = cursor.getString(cursor.getColumnIndex(TableContacts.picturePath));
            pub = cursor.getString(cursor.getColumnIndex(TableContacts.publickKey));
            type = cursor.getInt(cursor.getColumnIndex(TableContacts.type));
            try {
                publickKey = PublickKeyConverter.fromString(pub);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException("Should never happen " + e.getMessage());
            }


            ContactDAO contact = new ContactDAO(new ID(ID), publickKey, email,first,last,type, picture);
            return contact;

        }








}