package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import javax.crypto.SecretKey;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.model.EncryptionKeyDAO;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.KeyID;

public class TableEncryptionKeys extends Table<EncryptionKeyDAO, KeyID> implements EncryptionKeyHandler {


    public static final String TABLE_NAME = "Keys";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String TYPE = "TYPE";
    public static final String TIME = "TIME";
    public static final String SENDER_ID = "SENDER_ID";




    public static final String createTable() {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + SENDER_ID + "	INTEGER,\r\n" +
                "	" + TYPE + "	INTEGER,\r\n" +
                "	" + TIME + "	INTEGER,\r\n" +
                "	" + SECRET_KEY + "	TEXT,\r\n" +
                "	PRIMARY KEY(" + SENDER_ID + "," + TYPE + ")\r\n" +
                ");";

        return createTable;

    }

    public TableEncryptionKeys(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }


    @Override
    protected ContentValues putIntoContentValues(EncryptionKeyDAO EncryptionKeyDAO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_ID,EncryptionKeyDAO.getKeyID().getSenderID().toLong());
        contentValues.put(TYPE,EncryptionKeyDAO.getKeyID().getKeyType());
        contentValues.put(SECRET_KEY, EncryptionKeyDAO.getSecretKeyString());
        contentValues.put(TIME, EncryptionKeyDAO.getTime());

        return contentValues;
    }


    protected EncryptionKeyDAO readFromCursor(Cursor cursor) {
        long senderID;
        int type;
        String secretKEY;
        long time;

        secretKEY  = cursor.getString(cursor.getColumnIndex(SECRET_KEY));
        senderID = cursor.getLong(cursor.getColumnIndex(SENDER_ID));
        type = cursor.getInt(cursor.getColumnIndex(TYPE));
        time = cursor.getLong(cursor.getColumnIndex(TIME));

        KeyID keyID = new KeyID(senderID, type);
        SecretKey key = SecretKeyConventer.fromString(secretKEY);
        return new EncryptionKeyDAO(keyID,key,time);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(KeyID keyID) {
        String query  = SENDER_ID + " = "+keyID.getSenderID().toLong() +" AND "+TYPE +" = "+keyID.getKeyType();
        return query;
    }


    public EncryptionKeyDAO getEncryptionKey(ID senderID,int type) throws ItemNotFoundException {
        return this.getData(new KeyID(senderID, type));
    }


    @Override
    public EncryptionKey getEncryptionKey(KeyID keyID) throws ItemNotFoundException {
        return  this.getData(keyID);
    }

    @Override
    public EncryptionKey getEncryptionKeyWithTheLowestType(ID senderID) throws ItemNotFoundException {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();


        String query = "SELECT  * FROM " + getTableName() +
                "\n WHERE " +  SENDER_ID + " = "+senderID.toLong()
                +"\n ORDER BY "+TYPE+" ;";

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.getCount() == 0) {
            throw new ItemNotFoundException(getTableName(), senderID.toString());
        }
        cursor.moveToFirst();

        EncryptionKey result = readFromCursor(cursor);
        return result;
    }



    @Override
    public boolean addEncryptionKey(EncryptionKey encryptionKey) {
        return this.insertORReplace(new EncryptionKeyDAO(encryptionKey));
    }

    public boolean removeEncryptionKey(KeyID keyID)
    {
        return this.removeData(keyID);
    }


}
