package world.database.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import world.skytale.databases.model.DisplayableDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.Displayable;


/**
 * A class
 */
public class DisplayableSQLHelper {


    public static final String TEXT = "MESSAGE_TEXT";
    public static final String ATTACHMENTS = "ATTACHMENT_PATHS";
    public static final String LINK = "MAIN_LINK";
    public static final String CONFIGURATION = "CONFIGURATION_STRING";



    public static String getCreateColums()
    {
        String createColums = ""+
                "	" + LINK + "	TEXT,\r\n" +
                "	" + TEXT + "	TEXT,\r\n" +
                "	" + ATTACHMENTS + "	TEXT,\r\n" +
                "	" + CONFIGURATION + "	TEXT,\r\n";
        return createColums;
    }

    public static ContentValues putIntoContentValues(ContentValues contentValues, DisplayableDAO displayable)
    {
        contentValues.put(TEXT, displayable.getText());
        contentValues.put(LINK, displayable.getLink());
        contentValues.put(CONFIGURATION, displayable.getConfiguration());
        contentValues.put(ATTACHMENTS, pathsToString(displayable.getAttachmentPaths()));
        return contentValues;
    }

    public static DisplayableDAO readFromCursor(Cursor cursor)
    {
        String link,text,configuraiton,attachments;

        text = cursor.getString(cursor.getColumnIndex(TEXT));
        attachments = cursor.getString(cursor.getColumnIndex(ATTACHMENTS));
        link = cursor.getString(cursor.getColumnIndex(LINK));
        configuraiton = cursor.getString(cursor.getColumnIndex(CONFIGURATION));


        DisplayableDAO displayableDAO = new DisplayableDAO();

        displayableDAO.setText(text);
        displayableDAO.setAttachments(pathsFromString(attachments));
        displayableDAO.setConfiguration(configuraiton);
        displayableDAO.setLink(link);

        return displayableDAO;
    }

    private static final String SPLIT_CHAR = ";";
    private static  String pathsToString(String [] paths)
    {
        if(paths==null||paths.length==0)
        {
            return "";
        }

        String tmp = "";
        for(String path : paths)
        {
            tmp+=SPLIT_CHAR+path;
        }
        return tmp.substring(1);
    }

    private static String [] pathsFromString(String paths)
    {
        return paths.split(SPLIT_CHAR);
    }


    public static DisplayableDAO createDAO(Displayable displayable, FilesHandlerImpl filesHandler)
    {
        if(displayable==null)
        {
            return new DisplayableDAO();
        }
        DisplayableDAO displayableDAO = new DisplayableDAO();
        displayableDAO.setText(displayable.getText());
        displayableDAO.setLink(displayable.getLink());
        displayableDAO.setAttachments(filesHandler.saveAttachments(displayable.getAttachments()));
        displayableDAO.setConfiguration(displayable.getConfiguration());

        return displayableDAO;
    }



}
