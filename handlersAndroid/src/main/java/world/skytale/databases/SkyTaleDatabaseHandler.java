package world.skytale.databases;

import android.content.Context;

import world.database.AccountProvider;
import world.database.ChatHandler;
import world.database.ChatMessageHandler;
import world.database.CommentsHandler;
import world.database.ContactsHandler;
import world.database.DatabaseHandler;
import world.database.EncryptionKeyHandler;
import world.database.FriendRequestHandler;
import world.database.PostHandler;
import world.database.ProfilePageHandler;
import world.database.Tables.ProfilePageTable;
import world.database.Tables.TableComments;
import world.database.Tables.TableEncryptionKeys;
import world.database.Tables.TableFriendRequest;
import world.database.Tables.TablePosts;
import world.skytale.databases.files.FilesHandlerImpl;
import world.skytale.model.Account;
import world.skytale.model.EncryptionKey;

public class SkyTaleDatabaseHandler implements DatabaseHandler, AccountProvider {



    SQLDatabaseHelper databaseHelper;
    UserAccount account;
    FilesHandlerImpl filesHandler;


    public SkyTaleDatabaseHandler(Context context, UserAccount account)
    {
        this.account = account;
        this.databaseHelper = new SQLDatabaseHelper(context);
        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
        }
    }
    public SkyTaleDatabaseHandler(SQLDatabaseHelper databaseHelper, UserAccount account, Context context) {
        this.databaseHelper = databaseHelper;
        this.account=account;

        try {
            this.filesHandler = FilesHandlerImpl.getInstance(context);
        } catch (FilesHandlerImpl.StoragePermissionDeniedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccountProvider getAccountProvider() {
      return this;
    }

    @Override
    public ContactsHandler getContactsHandler() {
        return databaseHelper.getTableContacts();
    }

    @Override
    public ChatHandler getChatHandler() {
        return databaseHelper;
    }

    @Override
    public ChatMessageHandler getChatMessageHandler() {
        return databaseHelper;
    }

    @Override
    public PostHandler getPostHandler() {
        return new TablePosts(databaseHelper, filesHandler);
    }

    @Override
    public EncryptionKeyHandler getEncryptionKeyHandler() {
     return   new TableEncryptionKeys(databaseHelper);
    }

    @Override
    public FriendRequestHandler getFriendRequestHandler() {
        return new TableFriendRequest(databaseHelper);
    }

    @Override
    public ProfilePageHandler getProfilePageHandler() {
        return new ProfilePageTable(databaseHelper);
    }

    @Override
    public CommentsHandler getCommentsHandler() {
        return new TableComments(databaseHelper,filesHandler);
    }

    @Override
    public Account getCurrentAccount() {
        return account;
    }

    @Override
    public boolean updatePostEncryptionKeys(EncryptionKey friendsPostEncryptionKey, EncryptionKey followersEncryptionKey) {
        this.account.setFollowersPostEncryptionKey(followersEncryptionKey);
        this.account.setFriendsPostEncryptionKey(friendsPostEncryptionKey);
        return true;
    }
}
