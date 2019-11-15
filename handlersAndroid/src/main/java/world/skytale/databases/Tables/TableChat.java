package world.skytale.databases.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import world.skytale.converters.LongConverter;
import world.skytale.model2.ID;
import world.skytale.databases.daos.ChatMessageDAO;


public class TableChat {

    public static final String DATABASE_NAME= "SkyTale.db";
    

    public static final String senderID = "senderID";
    public static final String time = "time";
    public static final String message = "message";
    public static final String atachments = "atachments";
    public static final String recivedTime = "recivedTime";
    
    public static final String getTableName(ID chatID)
    {
        return  "Chat"+ LongConverter.toBase32String(Math.abs(chatID.toLong()));
    }


    public static String createTable(ID chatID)
    {

        final String TABLE_NAME = getTableName(chatID);
        String c2 = "CREATE TABLE "+TABLE_NAME+" (\r\n" +
                "	"+senderID+"	INTEGER,\r\n" +
                "	"+time+"	INTEGER,\r\n" +
                "	"+recivedTime+"	INTEGER,\r\n" +
                "	"+message+"	TEXT,\r\n" +
                "	"+atachments+"	TEXT,\r\n" +
                "	PRIMARY KEY("+senderID+","+time+")\r\n" +
                ");";

        return  c2;
    }
    public static  boolean addData(SQLiteDatabase db, ChatMessageDAO msg, ID chatID)
    {

        ContentValues contentValues = putChatMessageIntoContentValues(msg);
        final String TABLE_NAME = getTableName(chatID);
        long result = db.insert(TABLE_NAME, null, contentValues);

        db.close();
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }


    }
    private static ContentValues putChatMessageIntoContentValues(ChatMessageDAO msg)
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put(senderID,msg.getLongID());
        contentValues.put(time,msg.getTime());
        contentValues.put(message,msg.message);
        contentValues.put(recivedTime,msg.recivedTime);
        if(msg.attachments==null)
        {
            contentValues.put(atachments, "");
        }
        else
        {
            contentValues.put(atachments,msg.aatachmentsToString());
        }
        return contentValues;
    }

    public static ArrayList<ChatMessageDAO> selectAll(SQLiteDatabase db, ID chatID) {
        String TABLE_NAME = getTableName(chatID);


        String query = "SELECT  * FROM " + TABLE_NAME +
                "\n order BY " + TableChat.recivedTime + " DESC;";

        Cursor cursor = db.rawQuery(query, null);
        int length = cursor.getCount();


        ArrayList<ChatMessageDAO> tmp = new ArrayList<ChatMessageDAO>();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            ChatMessageDAO t = readChatMessageFromCursor(cursor);
            tmp.add(t);

            cursor.moveToNext();
        }

        return tmp;
    }

    public static ChatMessageDAO readChatMessageFromCursor(Cursor cursor)
    {
        String message, atachments;
        long time,senderID;

        senderID = cursor.getLong(cursor.getColumnIndex(TableChat.senderID));
        message = cursor.getString(cursor.getColumnIndex(TableChat.message));
        atachments = cursor.getString(cursor.getColumnIndex(TableChat.atachments));

        time = cursor.getLong(cursor.getColumnIndex(TableChat.time));

        long recivedTime = cursor.getLong(cursor.getColumnIndex(TableChat.recivedTime));
        ChatMessageDAO t = ChatMessageDAO.recreateMessage(senderID,message,time,atachments,recivedTime);

        return t;

    }

    public static void removeChat(SQLiteDatabase db,ID chatID)
    {
       final String TABLE_NAME = getTableName(chatID);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void removeMessage(SQLiteDatabase db,ID chatID,  String senderID, long time)
    {
        final String TABLE_NAME = getTableName(chatID);
        String query = "DELETE FROM " + TABLE_NAME+ " WHERE " + TableChat.senderID + " = '" + senderID + "' AND "+TableChat.time+" = "+time;
        db.execSQL(query);

    }






}
