package world.skytale.databases;

import world.skytale.database.AccountProvider;
import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.database.ContactsHandler;
import world.skytale.database.DatabaseHandler;
import world.skytale.database.EncryptionKeyHandler;
import world.skytale.database.PostHandler;
import world.skytale.databases.Tables.TableEncryptionKeys;

public class SkyTaleDatabaseHandler implements DatabaseHandler {



    SQLDatabaseHelper databaseHelper;
    AccountProvider accountProvider;

    public SkyTaleDatabaseHandler(SQLDatabaseHelper databaseHelper, AccountProvider accountProvider) {
        this.databaseHelper = databaseHelper;
        this.accountProvider = accountProvider;
    }

    @Override
    public AccountProvider getAccountProvider() {
      return accountProvider;
    }

    @Override
    public ContactsHandler getTableContacts() {
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
        return null;
    }

    @Override
    public EncryptionKeyHandler getEncryptionKeyHandler() {
     return   new TableEncryptionKeys(databaseHelper);
    }
}
