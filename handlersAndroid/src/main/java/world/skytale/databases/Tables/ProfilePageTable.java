package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import world.skytale.database.ItemNotFoundException;
import world.skytale.database.ProfilePageHandler;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.conventers.StringListConventer;
import world.skytale.databases.daos.ProfilePageDAO;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model2.Attachment;
import world.skytale.model2.ID;
import world.skytale.model2.ProfilePage;

public class ProfilePageTable extends Table<ProfilePageDAO, ID> implements ProfilePageHandler {


    public static final String TABLE_NAME = "ProfilePages";

    public static final String CONTACT_ID = "ID";
    public static final String USERNAME = "username";
    public static final String PICTURE_PATH = "profilePicture";
    public static final String DESCRIPTION = "description";
    public static final String LINKS = "profileLinks";


    public static final String createTable() {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (\r\n" +
                "	" + CONTACT_ID + "	INTEGER NOT NULL,\r\n" +
                "	"+ USERNAME+"	TEXT,\r\n" +
                "	"+ PICTURE_PATH +"	TEXT,\r\n" +
                "	" + LINKS + "	TEXT,\r\n" +
                "	" + DESCRIPTION + "	TEXT,\r\n" +
                "	PRIMARY KEY(" + CONTACT_ID  + ")\r\n" +
                ");";

        return createTable;

    }

    private final FilesHandlerImpl filesHandler;


    protected ProfilePageTable(SQLDatabaseHelper sqlDatabaseHelper, FilesHandlerImpl filesHandler) {
        super(sqlDatabaseHelper);
        this.filesHandler = filesHandler;
    }



    @Override
    protected ProfilePageDAO readFromCursor(Cursor cursor) {
        long contactID;
        String username,paiutePath,links,description;

        contactID = cursor.getLong(cursor.getColumnIndex(CONTACT_ID));
        username = cursor.getString(cursor.getColumnIndex(USERNAME));
        paiutePath = cursor.getString(cursor.getColumnIndex(PICTURE_PATH));
        links = cursor.getString(cursor.getColumnIndex(LINKS));
        description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));

        ArrayList<String> linksList = StringListConventer.fromString(links);

        return new ProfilePageDAO(contactID,username,paiutePath,description,linksList);
    }

    @Override
    protected ContentValues putIntoContentValues(ProfilePageDAO profilePageDAO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_ID, profilePageDAO.getConstactID().toLong());
        contentValues.put(USERNAME, profilePageDAO.getUsername());
        contentValues.put(PICTURE_PATH,profilePageDAO.getImagePath());
        contentValues.put(DESCRIPTION, profilePageDAO.getDescription());

        String links = StringListConventer.toString(profilePageDAO.getProfileLinks());
        contentValues.put(LINKS, links);

        return contentValues;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getWhereCondition(ID id) {
        String condition = CONTACT_ID +" = "+id.toLong();
        return condition;
    }


    private String saveProfilePicture(ID contactID, Attachment profilePicture)  {
        String path="";
        try {
           path = filesHandler.saveAttachment(profilePicture);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return path;
    }

    private String updateProfilePicture(ID contactID, Attachment profilePicture) throws ItemNotFoundException {
        if(profilePicture==null)
        {
            return "";
        }

        ProfilePageDAO profilePageDAO = getData(contactID);
        String previousImagePath = profilePageDAO.getImagePath();

        filesHandler.deleteFile(previousImagePath);
        return  saveProfilePicture(contactID, profilePicture);
    }




    public ProfilePageDAO getDAO(ProfilePage profilePage, String imagePath)
    {
        return new ProfilePageDAO(profilePage.getConstactID().toLong(),profilePage.getUsername(),imagePath,profilePage.getDescription(),profilePage.getProfileLinks());
    }


    @Override
    public boolean addProfilePage(ProfilePage profilePage) {
        String profileImagePath = saveProfilePicture(profilePage.getConstactID(), profilePage.getProfilePicture());
        ProfilePageDAO dao = getDAO(profilePage, profileImagePath);
        return this.addData(dao);
    }

    @Override
    public boolean updateProfilePage(ProfilePage profilePage) throws ItemNotFoundException {
        String profileImagePath = updateProfilePicture(profilePage.getConstactID(), profilePage.getProfilePicture());
        ProfilePageDAO dao = getDAO(profilePage, profileImagePath);
        return this.updateData(dao, profilePage.getConstactID());
    }

    @Override
    public ProfilePageDAO getProfilePage(ID id) throws ItemNotFoundException {
        return this.getData(id);
    }

    @Override
    public boolean deleteProfilePage(ID contactID) {
        return false;
    }
}
