package world.database.Tables;

import android.database.Cursor;

import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.Sendable;

public abstract class SendableTable <T extends Sendable>  extends Table <T, MessageID> {

    public static final String SENDER_ID = "SENDER_ID";
    public static final String TIME = "TIME";



    public SendableTable(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }



    @Override
    protected String getWhereCondition(MessageID messageID) {
        String query  = SENDER_ID + " = "+messageID.getSenderID().toLong() +" AND "+TIME +" = "+messageID.getTime();
        return query;
    }

    public static MessageID readMessageIDFromCursor(Cursor cursor)
    {
        long id,time;
        id = cursor.getLong(cursor.getColumnIndex(SENDER_ID));
        time = cursor.getLong(cursor.getColumnIndex(TIME));
        return new MessageID(id,time);
    }
}
