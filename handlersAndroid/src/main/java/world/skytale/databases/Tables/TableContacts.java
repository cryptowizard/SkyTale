package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import world.skytale.converters.PublickKeyConverter;
import world.skytale.database.ContactsHandler;
import world.skytale.model.Contact;
import world.skytale.model.ID;

public class TableContacts {



        public static final String TABLE_NAME = "Contacts";

        public static final String ID = "ID";
        public static final String firstName = "firstName";
        public static final String lastName = "lastName";
        public static final String email = "email";
        public static final String picturePath = "picturePath";
        public static final String type = "contactType";
        public static final String publickKey = "publicKey";


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


        public static boolean addData(SQLiteDatabase db, Contact contact) {
                ContentValues contentValues = putContactIntoContentValues(contact);
                long result = db.insert(TABLE_NAME, null, contentValues);

                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
        }

        private static ContentValues putContactIntoContentValues(Contact contact) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(ID, contact.contactID.toLong());
            contentValues.put(firstName, contact.firstName);
            contentValues.put(lastName, contact.lastName);
            contentValues.put(email, contact.address);
            contentValues.put(picturePath, contact.picturePath);
            contentValues.put(publickKey, PublickKeyConverter.toString(contact.publicKey));
            contentValues.put(type, contact.contactType);

            return contentValues;
        }




        public static Contact getContact(SQLiteDatabase db, long id) throws ContactsHandler.ContactNotFoundException {
            String query = "SELECT  * FROM " + TABLE_NAME +
                    "\n WHERE " + ID + " = '" + id + "';";

            Cursor cursor = db.rawQuery(query, null);


            if (cursor.getCount() == 0) {
                throw new ContactsHandler.ContactNotFoundException(new ID(id));
            }
            cursor.moveToFirst();
            Contact contact = readFromCursor(cursor);
            return contact;


        }

        private static Contact readFromCursor(Cursor cursor) {
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


            Contact contact = new Contact(new ID(ID), publickKey, first, last, email, picture, type);
            return contact;

        }


        public static int setContactType(SQLiteDatabase db, Long ID, int type) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableContacts.type, type);
            return db.update(TableContacts.TABLE_NAME, contentValues, TableContacts.ID + " = " + ID + "", null);
        }


        public static int updateContact(SQLiteDatabase db, Contact contact) {
            ContentValues contentValues = putContactIntoContentValues(contact);
            return db.update(TableContacts.TABLE_NAME, contentValues, TableContacts.ID + "= " + contact.contactID.toLong() + "", null);

        }

}