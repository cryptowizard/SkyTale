package world.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import world.database.ItemNotFoundException;
import world.database.PostHandler;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.model.DisplayableDAO;
import world.skytale.databases.model.PostDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.Post;

public class TablePosts implements PostHandler {


    public static final String TABLE_NAME = "Posts";

    public static final String senderID = "senderID";

    public static final String time = "time";
    public static final String orginalSenderID = "orginalSenderID";
    public static final String liked = "liked";
    public static final String recivedTime = "recivedTime";
    public static final String numberOFComments = "numberOfComments";


    private final SQLDatabaseHelper sqlDatabaseHelper;
    private final FilesHandlerImpl filesHandler;

    public TablePosts(SQLDatabaseHelper sqlDatabaseHelper, FilesHandlerImpl filesHandler) {
        this.sqlDatabaseHelper = sqlDatabaseHelper;
        this.filesHandler = filesHandler;
    }

    public static final String createTable() {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + senderID + "	INTEGER,\r\n" +
                "	" + time + "	INTEGER,\r\n" +
                "	" + recivedTime + "	INTEGER,\r\n" +
                "	" + liked + "	INTEGER DEFAULT 0,\r\n" +
                "	" + orginalSenderID + "	INTEGER,\r\n" +
                "	" + numberOFComments + "	INTEGER DEFAULT 0,\r\n" +
                DisplayableSQLHelper.getCreateColums()+
                "	PRIMARY KEY(" + senderID + "," + time + ")\r\n" +
                ");";

        return createTable;
    }



    public PostDAO getPost(ID sendersID, long time) throws ItemNotFoundException {
        return getPost(sqlDatabaseHelper.getReadableDatabase(), sendersID, time);
    }

    @Override
    public Post getPost(MessageID messageID) throws ItemNotFoundException {
        return getPost(sqlDatabaseHelper.getReadableDatabase(), messageID.getSenderID(),messageID.getTime());
    }

    @Override
    public boolean addPost(Post post) {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        PostDAO postDAO = createDAO(post);

        ContentValues contentValues = putPostIntoContentValues(postDAO);

        long result = db.insert(TABLE_NAME, null, contentValues);

        sqlDatabaseHelper.close();

        if (result == -1) return false;
        return true;
    }

    @Override
    public boolean removePost(Post post) {

       removePost(sqlDatabaseHelper.getWritableDatabase(),post.getMessageID().getSenderID(), post.getMessageID().getTime());
        return true;
    }


    private PostDAO createDAO(Post post)
    {
        PostDAO postDAO = new PostDAO(post.getMessageID().getSenderID(), post.getMessageID().getTime());
        postDAO.setOrdinalSenderID(post.getOrdinalSendersID());

        postDAO.setDisplayableDAO(DisplayableSQLHelper.createDAO(post.getDisplayable(),filesHandler));

        return postDAO;
    }


    private static ContentValues putPostIntoContentValues(PostDAO post) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(senderID, post.getSenderID().toLong());
        contentValues.put(time, post.time);
        contentValues.put(orginalSenderID, post.getOrdinalSendersID().toLong());
        contentValues.put(recivedTime, post.receivedTime);
        DisplayableSQLHelper.putIntoContentValues(contentValues,post.getDisplayable());
        return contentValues;

    }




    private static ArrayList<PostDAO> readCursor(Cursor cursor)
    {
        int length = cursor.getCount();

        ArrayList<PostDAO> tmp = new ArrayList<PostDAO>(length);
        if (length == 0) {
            return tmp;
        }


        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            PostDAO post = fromCursor(cursor);
            tmp.add(post);
            cursor.moveToNext();
        }

        return tmp;
    }

    private static PostDAO fromCursor(Cursor cursor)
    {
        String text,attachments,link;
        int liked, numberOfComments;
        long time, senderID, orginalSenderID;
        long recivedTime ;
        DisplayableDAO displayableDAO;



        senderID = cursor.getLong(cursor.getColumnIndex(TablePosts.senderID));
        time = cursor.getLong(cursor.getColumnIndex(TablePosts.time));
        orginalSenderID = cursor.getLong(cursor.getColumnIndex(TablePosts.orginalSenderID));
        liked = cursor.getInt(cursor.getColumnIndex(TablePosts.liked));
        recivedTime = cursor.getLong(cursor.getColumnIndex(TablePosts.recivedTime));

        displayableDAO = DisplayableSQLHelper.readFromCursor(cursor);

        boolean isLiked = (liked>0);

        PostDAO postDAO = new PostDAO(new ID(senderID),time);


        postDAO.setOrdinalSenderID(new ID(orginalSenderID));
        postDAO.setLiked(isLiked);
        postDAO.setReceivedTime(recivedTime);
        postDAO.setDisplayableDAO(displayableDAO);

        return postDAO;


    }

    public static boolean addPost(SQLiteDatabase db, PostDAO post) {

        ContentValues contentValues = putPostIntoContentValues(post);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) return false;
        else return true;

    }


    public static ArrayList<PostDAO> getAllPosts(SQLiteDatabase db)
    {

        String query = "SELECT  * FROM " + TABLE_NAME +
                "\n ORDER BY " +recivedTime + " DESC;";


        Cursor cursor = db.rawQuery(query, null);
        int length = cursor.getCount();

        ArrayList<PostDAO> tmp = new ArrayList<PostDAO>(length);
        if (length == 0) {
            return tmp;
        }
        
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            PostDAO post = fromCursor(cursor);
            tmp.add(post);
            cursor.moveToNext();
        }
        return tmp;
    }



    public static ArrayList<PostDAO> getAllPosts(SQLiteDatabase db, long toTime)
    {

        String query = "SELECT  * FROM " + TABLE_NAME  +
                "\n WHERE "+TablePosts.time+" <= "+toTime+
                "\n order BY " + recivedTime + " DESC;";


        Cursor cursor = db.rawQuery(query, null);
        return readCursor(cursor);
    }

    public static ArrayList<PostDAO> getAllPosts(SQLiteDatabase db, ID senderID)
    {

        String query = "SELECT  * FROM " + TABLE_NAME  +
                "\n WHERE "+TablePosts.senderID+" "+senderID.toLong()+""+
                "\n order BY " + recivedTime + " DESC;";


        Cursor cursor = db.rawQuery(query, null);
        return readCursor(cursor);
    }

  


    public static boolean likePost(SQLiteDatabase db, ID senderID, long time)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablePosts.liked,1);
        int result =  db.update(TablePosts.TABLE_NAME, contentValues,TablePosts.senderID +" = '"+senderID.toLong()+"' AND "+TablePosts.time +" = "+time,null);

        if (result == -1) return false;
        else return true;
    }

    public static PostDAO getPost(SQLiteDatabase db, ID senderID, long time) throws ItemNotFoundException {
        String query = "SELECT  * FROM " + TABLE_NAME +
                "\n WHERE " + TablePosts.senderID +" = '"+senderID.toLong()+"' AND "+TablePosts.time +" = "+time+";" ;

        Cursor cursor = db.rawQuery(query, null);


        if(cursor.getCount()==0)
        {
            throw  new ItemNotFoundException(TablePosts.TABLE_NAME," post not found Exception");
        }
        cursor.moveToFirst();
        return fromCursor(cursor);
    }

    public static void removePost(SQLiteDatabase db, ID senderID, long time)
    {
        String query = "DELETE FROM " + TABLE_NAME+ " WHERE " + TablePosts.senderID + " = '" + senderID.toLong() + "' AND "+TablePosts.time+" = "+time;
     db.execSQL(query);
    }

    public static void incrementNumberOfComments(SQLiteDatabase db, ID senderID, long time)
    {
        db.execSQL("UPDATE " + TABLE_NAME +
                " SET " + numberOFComments + " = " + numberOFComments + " + 1" +
                " WHERE " + TablePosts.senderID + " = '"+senderID.toLong()+"' AND "+TablePosts.time+" = "+time);

    }

    public static void decrementNumberOfComments(SQLiteDatabase db, ID senderID, long time)
    {
        db.execSQL("UPDATE " + TABLE_NAME +
                " SET " + numberOFComments + " = " + numberOFComments + " - 1" +
                " WHERE " + TablePosts.senderID + " = '"+senderID.toLong()+"' AND "+TablePosts.time+" = "+time);

    }


}
