package world.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import world.skytale.converters.LongConverter;
import world.skytale.databases.daos.ChatMessageDAO;
import world.skytale.databases.daos.DisplayableDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.ID;
import world.skytale.model.sendable.ChatMessage;


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
                DisplayableSQLHelper.getCreateColums()+
                "	PRIMARY KEY("+senderID+","+time+")\r\n" +
                ");";

        return  c2;
    }
    public static  boolean addData(SQLiteDatabase db, ChatMessageDAO msg)
    {

        ContentValues contentValues = putChatMessageIntoContentValues(msg);
        final String TABLE_NAME = getTableName(msg.getChatID());
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

        contentValues.put(senderID,msg.getMessageID().getSenderID().toLong());
        contentValues.put(time,msg.getMessageID().getTime());
        contentValues.put(recivedTime,msg.getRecivedTime());
        DisplayableSQLHelper.putIntoContentValues(contentValues,msg.getDisplayable());

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
            ChatMessageDAO t = readChatMessageFromCursor(cursor, chatID);
            tmp.add(t);

            cursor.moveToNext();
        }

        return tmp;
    }

    public static ChatMessageDAO readChatMessageFromCursor(Cursor cursor, ID chatID)
    {
        long time,senderID;

        senderID = cursor.getLong(cursor.getColumnIndex(TableChat.senderID));
        long recivedTime = cursor.getLong(cursor.getColumnIndex(TableChat.recivedTime));
        time = cursor.getLong(cursor.getColumnIndex(TableChat.time));

        DisplayableDAO displayableDAO = DisplayableSQLHelper.readFromCursor(cursor);


        ChatMessageDAO t = new ChatMessageDAO(new ID(senderID),time,chatID);
        t.setDisplayable(displayableDAO);
        t.setRecivedTime(recivedTime);

        return t;

    }

    public static ChatMessageDAO toDAO(ChatMessage chatMessage, FilesHandlerImpl filesHandler)
    {
        DisplayableDAO displayableDAO = DisplayableSQLHelper.createDAO(chatMessage.getDisplayable(), filesHandler);
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO(chatMessage.getMessageID().getSenderID(),chatMessage.getMessageID().getTime(),chatMessage.getChatID());
        chatMessageDAO.setDisplayable(displayableDAO);
        return chatMessageDAO;
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
