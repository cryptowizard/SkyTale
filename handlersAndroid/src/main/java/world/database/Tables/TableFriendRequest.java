package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import world.database.FriendRequestHandler;
import world.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.model.FriendRequestDao;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.FriendRequest;

public class TableFriendRequest extends Table<FriendRequestDao, MessageID> implements FriendRequestHandler
{
    public static final String TABLE_NAME = "Requests";

    public static final String SENDER_ID= "sednderID2";
    public static final String RECIVERS_EMAIL = "type";
    public static final String TIME= "time";
    public static final String RECIVED_TIME = "RECIVED_TIME";

    public static final String createTable () {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + SENDER_ID + "	INTEGER,\r\n" +
                "	" + TIME + "	INTEGER,\r\n" +
                "	" + RECIVED_TIME + "	INTEGER,\r\n" +
                "	" + RECIVERS_EMAIL + "	TEXT,\r\n" +
                "	PRIMARY KEY(" + SENDER_ID+ "," + TIME + ")\r\n" +
                ");";

        return createTable;
    }

    public TableFriendRequest(SQLDatabaseHelper sqlDatabaseHelper)  {
        super(sqlDatabaseHelper);
    }

    @Override
    protected FriendRequestDao readFromCursor(Cursor cursor) {
        long time, recivedTime, senderID;
        String recivedEmail;

        senderID = cursor.getLong(cursor.getColumnIndex(SENDER_ID));
        time = cursor.getLong(cursor.getColumnIndex(TIME));
        recivedTime = cursor.getLong(cursor.getColumnIndex(RECIVED_TIME));
        recivedEmail = cursor.getString(cursor.getColumnIndex(RECIVERS_EMAIL));

        return new FriendRequestDao(recivedEmail,new ID(senderID), time,recivedTime);
    }

    @Override
    protected ContentValues putIntoContentValues(FriendRequestDao friendRequestDao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_ID,friendRequestDao.getMessageID().getSenderID().toLong());
        contentValues.put(TIME,friendRequestDao.getMessageID().getTime());
        contentValues.put(RECIVED_TIME, friendRequestDao.getRecivedTime());
        contentValues.put(RECIVERS_EMAIL, friendRequestDao.getReciversEmail());
        return contentValues;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(MessageID messageID) {
        String query  = SENDER_ID + " = "+messageID.getSenderID().toLong() +" AND "+TIME +" = "+messageID.getTime();
        return query;
    }

    @Override
    public boolean addFriendRequest(FriendRequest friendRequest) {
        FriendRequestDao friendRequestDao = new FriendRequestDao(friendRequest.getReciversEmail(),friendRequest.getMessageID().getSenderID(),friendRequest.getMessageID().getTime());
        return this.addData(friendRequestDao);
    }

    @Override
    public FriendRequest getFriendRequest(MessageID messageID) throws ItemNotFoundException {
        return this.getData(messageID);
    }

    @Override
    public boolean removeFriendRequest(FriendRequest friendRequest) {
        return this.removeData(new MessageID(friendRequest.getMessageID().getSenderID(), friendRequest.getMessageID().getTime()));
    }
}
