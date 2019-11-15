package world.skytale.databases;

import world.skytale.database.AccountProvider;
import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.database.ContactsHandler;
import world.skytale.database.DatabaseHandler;

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
        return databaseHelper;
    }

    @Override
    public ChatHandler getChatHandler() {
        return databaseHelper;
    }

    @Override
    public ChatMessageHandler getChatMessageHandler() {
        return databaseHelper;
    }
}
