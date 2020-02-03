package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import world.skytale.converters.PublickKeyConverter;
import world.database.ContactsHandler;
import world.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model.Contact;
import world.skytale.model.ID;

public class TableContacts extends Table<ContactDAO, ID> implements ContactsHandler {



        public static final String TABLE_NAME = "Contacts";

        public static final String ID = "ID";
        public static final String email = "email";
        public static final String publickKey = "publicKey";
        public static final String IS_FRIEND = "isFriend";
        public static final String IS_OBSERVED = "isObserved";
        public static final String IS_FOLLOWER = "isFollower";




    public TableContacts(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }


    public static String CreateTable() {
            String createTableContacts = "CREATE TABLE " + TableContacts.TABLE_NAME + " (\r\n" +
                    "	" + TableContacts.ID + "	INTEGER NOT NULL,\r\n" +
                    "	" + TableContacts.email + "	TEXT NOT NULL,\r\n" +
                    "	" + TableContacts.publickKey + "	TEXT NOT NULL,\r\n"+
                    "	" + TableContacts.IS_FRIEND + "	INTEGER DEFAULT 0,\r\n" +
                    "	" + TableContacts.IS_FOLLOWER + "	INTEGER DEFAULT 0,\r\n" +
                    "	" + TableContacts.IS_OBSERVED + "	INTEGER DEFAULT 0,\r\n" +
                    "	PRIMARY KEY(" + TableContacts.ID + ")\r\n" +
                    ");";
            return createTableContacts;
        }

    @Override
    public Contact getContact(world.skytale.model.ID contactID) throws ItemNotFoundException {
        return this.getData(contactID);
    }

    @Override
    public boolean addContact(Contact contact) {
        return this.addData(makeDAO(contact));
    }

    @Override
    public boolean changeContactEmail(world.skytale.model.ID contactID, String newAddress) {
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
        ContactDAO contactDAO = new ContactDAO(contact.getID(),contact.getPublicKey(),contact.getAdress(),contact.isFriend(),contact.isFollower(),contact.isFriend());
        return contactDAO;
    }

    @Override
    public boolean setContactIsFriend(world.skytale.model.ID contactID, boolean isFriend) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_FRIEND, isFriend);
        return updateContact(contactID, contentValues);
    }

    @Override
    public boolean setContactIsFollower(world.skytale.model.ID contactID, boolean isFollower) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_FOLLOWER, isFollower);
        return updateContact(contactID, contentValues);
    }

    @Override
    public boolean setContactIsObserved(world.skytale.model.ID contactID, boolean isObserved) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_OBSERVED, isObserved);
        return updateContact(contactID, contentValues);
    }

    @Override
    public List<Contact> getAllContacts() {
        ArrayList<ContactDAO> list =  this.getAll();
        ArrayList<Contact> result = new ArrayList<Contact>(list);

        return result;
    }

    @Override
    public List<Contact> getAllContactsThat(boolean isFriend, boolean isFollower, boolean isObserved) {
        int friend = isFriend? 1 : 0;
        int follower = isFollower? 1: 0;
        int observed = isObserved? 1: 0;

        String condition = IS_FRIEND +" = " +friend+"," +
                           IS_FOLLOWER +" = " +follower+"," +
                        IS_OBSERVED +" = " +observed;

        String query = "SELECT  * FROM " + getTableName() +
                "\n WHERE " + condition+";";

        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return new ArrayList<Contact>(readAllFromCursor(cursor));
    }

    private boolean updateContact(ID contactID, ContentValues contentValues)
    {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, getWhereCondition(contactID), null);
        sqlDatabaseHelper.close();
        return (result>0);
    }
    @Override
       protected ContactDAO readFromCursor(Cursor cursor) {
            String email, pub;
            PublicKey publickKey = null;
            long ID;
            int isFriend,isFollower,isObserved;


            ID = cursor.getLong(cursor.getColumnIndex(TableContacts.ID));
            email = cursor.getString(cursor.getColumnIndex(TableContacts.email));
            pub = cursor.getString(cursor.getColumnIndex(TableContacts.publickKey));
            isFriend = cursor.getInt(cursor.getColumnIndex(IS_FRIEND));
            isFollower = cursor.getInt(cursor.getColumnIndex(IS_FOLLOWER));
            isObserved = cursor.getInt(cursor.getColumnIndex(IS_OBSERVED));
            try {
                publickKey = PublickKeyConverter.fromString(pub);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException("Should never happen " + e.getMessage());
            }


            ContactDAO contact = new ContactDAO(new ID(ID), publickKey, email);
            contact.setFriend(isFriend);
            contact.setFollower(isFollower);
            contact.setObserved(isObserved);
            return contact;

        }

    @Override
    protected ContentValues putIntoContentValues(ContactDAO contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, contact.getID().toLong());
        contentValues.put(email, contact.getAdress());
        contentValues.put(publickKey, PublickKeyConverter.toString(contact.getPublicKey()));
        contentValues.put(IS_FRIEND, contact.isFriend());
        contentValues.put(IS_FOLLOWER, contact.isFollower());
        contentValues.put(IS_OBSERVED, contact.isObserved());
        return contentValues;
    }



    @Override
    protected String getTableName() {
        return TableContacts.TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(world.skytale.model.ID id) {
        String condition = ID +" = "+id.toLong();
        return condition;
    }





}