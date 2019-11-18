package world.skytale;

import world.skytale.database.DatabaseHandler;

public class MessagesHandler extends IncomingMessageProcessor {

    private final DatabaseHandler databaseHandler;

    public MessagesHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    @Override
    protected DatabaseHandler getDatabaseHandler() {
        return this.databaseHandler;
    }
}
