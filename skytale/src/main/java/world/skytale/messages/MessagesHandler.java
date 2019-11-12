package world.skytale.messages;

import world.skytale.database.AccountProvider;
import world.skytale.database.DatabaseHandler;
import world.skytale.database.FilesHandler;
import world.skytale.messages.processors.ChatMessageProcessor;
import world.skytale.messages.processors.MessageProcessingException;
import world.skytale.messages.processors.MessageProcessor;

public class MessagesHandler {

    private DatabaseHandler DatabaseHandler;
    private AccountProvider AccountProvider;
    private FilesHandler filesHandler;

    public MessagesHandler(world.skytale.database.DatabaseHandler databaseHandler, world.skytale.database.AccountProvider accountProvider, FilesHandler filesHandler) {
        DatabaseHandler = databaseHandler;
        AccountProvider = accountProvider;
        this.filesHandler = filesHandler;
    }

    public world.skytale.database.DatabaseHandler getDatabaseHandler() {
        return DatabaseHandler;
    }

    public world.skytale.database.AccountProvider getAccountProvider() {
        return AccountProvider;
    }

    public FilesHandler getFilesHandler() {
        return filesHandler;
    }

    public void processIncomingMail(DownloadedMail downloadedMail) throws MessageProcessingException {

        MessageHeader messageHeader = MessageHeader.parseTitle(downloadedMail.getTitle());

        MessageProcessor processor= null;
        switch (messageHeader.getMessageType())
        {
            case ChatMessageProcessor.TYPE_TAG:
                processor = new ChatMessageProcessor(this);
                break;
        }

        if(processor==null)
        {
            throw new MessageProcessingException("Processing failed due to: unrecognized message Type");
        }


    }

}
