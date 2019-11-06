package world.skytale.Messages;

import world.skytale.database.AccountProvider;

public abstract class MessageHandler {

    protected abstract AccountProvider getAccountProvider();





    public boolean processIncomingMail()
    {
        return false;
    }

}
