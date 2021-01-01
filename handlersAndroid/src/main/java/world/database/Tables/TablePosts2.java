package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.PostDAO;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Post;

public class TablePosts2 extends Table<Post, MessageID> {


    public static final String TABLE_NAME = "Posts";

    public static final String senderID = "senderID";
    public static final String text = "text";
    public static final String attachments = "attachments";
    public static final String time = "time";
    public static final String orginalSenderID = "orginalSenderID";
    public static final String link = "link";
    public static final String liked = "liked";
    public static final String recivedTime = "recivedTime";
    public static final String numberOFComments = "numberOfComments";

    public TablePosts2(SQLDatabaseHelper sqlDatabaseHelper) {
        super(sqlDatabaseHelper);
    }

    @Override
    protected PostDAO readFromCursor(Cursor cursor) {
        return null;
    }

    @Override
    protected ContentValues putIntoContentValues(Post post) {
        return null;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(MessageID messageID) {
        return null;
    }
}
