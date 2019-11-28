package world.skytale.databases.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import world.skytale.database.ChatHandler;
import world.skytale.model.ID;
import world.skytale.databases.daos.ChatDAO;


public class TableChatList {

    public static final String TABLE_NAME = "ChatList";

    public static final String CHAT_ID = "CHAT_ID";
    public static final String conversationKey = "conversationKey";
    public static final String participants = "participants";
    public static final String newMessages = "newMessages";
    public static final String lastMessageTime = "lastMessageTime";
    public static final String chatImagePath = "chatImagePath";
    public static final String chatName = "chatName";


    public static String createTable()
    {
        String createTableChatList ="CREATE TABLE "+TABLE_NAME+" (\r\n" +
                "	"+ CHAT_ID +"	INTEGER NOT NULL,\r\n" +
                "	"+TableChatList.conversationKey+"	TEXT,\r\n" +
                "	"+TableChatList.participants +"	TEXT,\r\n" +
                "	"+TableChatList.newMessages+"	INTEGER,\r\n" +
                "	"+TableChatList.lastMessageTime+"	INTEGER,\r\n" +
                "	"+TableChatList.chatImagePath+"	TEXT,\r\n" +
                "	"+TableChatList.chatName+"	TEXT,\r\n" +
                "	PRIMARY KEY("+TableChatList.CHAT_ID +")\r\n" +
                ");";

        return createTableChatList;
    }

    public static boolean addData(SQLiteDatabase db, ChatDAO chat)
    {
        ContentValues contentValues =putChatIntoContentValues(chat);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    private static ContentValues putChatIntoContentValues(ChatDAO chat)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CHAT_ID,chat.getChatID().toLong());
        contentValues.put(conversationKey,chat.conversationKeyToString());
        contentValues.put(participants,chat.participantsToString());
        contentValues.put(newMessages,chat.newMessages);
        contentValues.put(lastMessageTime,chat.lastMessageTime);
        contentValues.put(chatImagePath,chat.chatImage);
        contentValues.put(chatName,chat.getChatName());

        return contentValues;
    }



    public static boolean updateChat(SQLiteDatabase db, ChatDAO chat)
    {
        ContentValues contentValues = putChatIntoContentValues(chat);
        int result =  db.update(TableChatList.TABLE_NAME, contentValues,TableChatList.CHAT_ID +" = "+chat.getChatID().toLong()+"",null);

        if(result >0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static ChatDAO getChat(SQLiteDatabase db, long chatID) throws ChatHandler.ChatNotFoundException {
        String query = "SELECT  * FROM " + TABLE_NAME +
                "\n WHERE " + CHAT_ID + " = "+ chatID + ";";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount()==0)
        {
            throw new ChatHandler.ChatNotFoundException(new ID(chatID));
        }

        cursor.moveToFirst();
        return readChatFromCursor(cursor);

    }

    private static ChatDAO readChatFromCursor(Cursor cursor)
    {
        String key, participants, picture, chatName;
        long lastMessageTime;
        int newMessages;
        long ID;
        ID = cursor.getLong(cursor.getColumnIndex(TableChatList.CHAT_ID));
        key = cursor.getString(cursor.getColumnIndex(TableChatList.conversationKey));
        participants = cursor.getString(cursor.getColumnIndex(TableChatList.participants));
        picture = cursor.getString(cursor.getColumnIndex(TableChatList.chatImagePath));
        lastMessageTime = cursor.getLong(cursor.getColumnIndex(TableChatList.lastMessageTime));
        newMessages= cursor.getInt(cursor.getColumnIndex(TableChatList.newMessages));
        chatName = cursor.getString(cursor.getColumnIndex(TableChatList.chatName));

        ChatDAO t = new ChatDAO(ID, key,participants,lastMessageTime,newMessages,picture,chatName);
        return t;
    }







}
