package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import world.database.CommentsHandler;
import world.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ReplyDAO;
import world.skytale.databases.daos.DisplayableDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Reply;

public class TableComments extends Table<ReplyDAO, MessageID> implements CommentsHandler {


    public static final String TABLE_NAME = "Comments";


    public static final String SENDER_ID = "SENDER_ID";
    public static final String TIME = "TIME";
    public static final String POST_SENDER_ID = "POST_SENDER_ID";
    public static final String POST_TIME= "POST_TIME";
    public static final String REPLY_ID = "REPLY_ID";
    public static final String REPLY_TIME = "REPLY_TIME";


    private final FilesHandlerImpl filesHandler;


    public static final String createTable()
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + SENDER_ID+ "	INTEGER,\r\n" +
                "	" + TIME + "	INTEGER,\r\n" +
                "	" + POST_SENDER_ID+ "	INTEGER,\r\n" +
                "	" + POST_TIME+ "	INTEGER,\r\n" +
                "	" + REPLY_ID+ "	INTEGER,\r\n" +
                "	" + REPLY_ID + "	INTEGER,\r\n" +
                DisplayableSQLHelper.getCreateColums()+
                "	PRIMARY KEY(" + SENDER_ID + "," + TIME + ")\r\n" +
                ");";

        return createTable;
    }

    public TableComments(SQLDatabaseHelper sqlDatabaseHelper, FilesHandlerImpl filesHandler) {
        super(sqlDatabaseHelper);
        this.filesHandler = filesHandler;
    }

    @Override
    protected ReplyDAO readFromCursor(Cursor cursor) {
        long senderID, time,postSenderID, postTime,replySenderID,replyTime;

        senderID = cursor.getLong(cursor.getColumnIndex(SENDER_ID));
        time = cursor.getLong(cursor.getColumnIndex(TIME));
        postSenderID = cursor.getLong(cursor.getColumnIndex(POST_SENDER_ID));
        postTime= cursor.getLong(cursor.getColumnIndex(POST_TIME));
        replySenderID= cursor.getLong(cursor.getColumnIndex(REPLY_ID));
        replyTime = cursor.getLong(cursor.getColumnIndex(REPLY_TIME));

        MessageID messageID = new MessageID(senderID,time);
        MessageID postID = new MessageID(postSenderID,postTime);
        MessageID replyID =null;

        if(replySenderID!=0)
            replyID = new MessageID(replySenderID,replyTime);

        DisplayableDAO displayableDAO = DisplayableSQLHelper.readFromCursor(cursor);


        return new ReplyDAO(messageID,postID,replyID,displayableDAO);
    }

    @Override
    protected ContentValues putIntoContentValues(ReplyDAO commentDAO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_ID,commentDAO.getMessageID().getSenderID().toLong());
        contentValues.put(TIME,commentDAO.getMessageID().getTime());
        contentValues.put(POST_SENDER_ID,commentDAO.getOrginalContentID().getSenderID().toLong());
        contentValues.put(POST_TIME,commentDAO.getOrginalContentID().getTime());

        if(commentDAO.getReplyID()!=null)
        {
            contentValues.put(REPLY_ID,commentDAO.getReplyID().getSenderID().toLong());
            contentValues.put(REPLY_TIME,commentDAO.getReplyID().getTime());
        }

        DisplayableSQLHelper.putIntoContentValues(contentValues,commentDAO.displayable);

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
    public Reply getComment(MessageID messageID) throws ItemNotFoundException {
        return getData(messageID);
    }

    @Override
    public boolean addComment(Reply reply) {


        return this.addData(toDAO(reply,filesHandler));
    }

    public static ReplyDAO toDAO(Reply reply, FilesHandlerImpl filesHandler)
    {
        DisplayableDAO displayableDAO = DisplayableSQLHelper.createDAO(reply.getDisplayable(),filesHandler);
        ReplyDAO commentDAO = new ReplyDAO(reply.getMessageID(), reply.getOrginalContentID(), reply.getReplyID(),displayableDAO);
        return commentDAO;
    }

    @Override
    public boolean removeComment(Reply reply) {
        return this.removeData(reply.getMessageID());
    }
}
