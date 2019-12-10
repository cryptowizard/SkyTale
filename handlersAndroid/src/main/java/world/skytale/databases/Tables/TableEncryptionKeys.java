package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import world.skytale.database.EncryptionKeyHandler;
import world.skytale.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.EncryptionKeyDAO;
import world.skytale.databases.daos.MessageID;
import world.skytale.model.sendable.EncryptionKey;
import world.skytale.model.ID;

public class TableEncryptionKeys extends Table<EncryptionKeyDAO, MessageID> implements EncryptionKeyHandler {


    public static final String TABLE_NAME = "Keys";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String TIME = "TIME";
    public static final String SENDER_ID = "SENDER_ID";




    public static final String createTable() {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + SENDER_ID + "	TEXT,\r\n" +
                "	" + TIME + "	INTEGER,\r\n" +
                "	" + SECRET_KEY + "	TEXT,\r\n" +
                "	PRIMARY KEY(" + SENDER_ID + "," + TIME + ")\r\n" +
                ");";

        return createTable;

    }

    public TableEncryptionKeys(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }


    @Override
    protected ContentValues putIntoContentValues(EncryptionKeyDAO encryptionKeyDAO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_ID,encryptionKeyDAO.getSenderID().toLong());
        contentValues.put(TIME,encryptionKeyDAO.getTime());
        contentValues.put(SECRET_KEY, encryptionKeyDAO.getSecretKey());

        return contentValues;
    }


    protected EncryptionKeyDAO readFromCursor(Cursor cursor) {
        long time, senderID;
        String secretKEY;

        secretKEY  = cursor.getString(cursor.getColumnIndex(SECRET_KEY));
        senderID = cursor.getLong(cursor.getColumnIndex(SENDER_ID));
        time = cursor.getLong(cursor.getColumnIndex(TIME));

        return new EncryptionKeyDAO(senderID,time,secretKEY);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(MessageID messageID) {
        String query  = SENDER_ID + " = "+messageID.getSenderID() +" AND "+TIME +" = "+messageID.getTime();
        return query;
    }

    @Override
    public EncryptionKeyDAO getEncryptionKey(ID senderID, long time) throws ItemNotFoundException {
        return this.getData(new MessageID(senderID.toLong(),time));
    }

    @Override
    public boolean addEncryptionKey(EncryptionKey encryptionKey) {
        return this.addData(new EncryptionKeyDAO(encryptionKey));
    }
}
