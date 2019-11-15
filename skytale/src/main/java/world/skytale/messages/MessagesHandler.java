package world.skytale.messages;

import java.io.IOException;

import world.skytale.database.DatabaseHandler;
import world.skytale.database.FilesHandler;
import world.skytale.messages.processors.ChatMessageProcessor;
import world.skytale.MessageProcessingException;
import world.skytale.messages.processors.MessageProcessor;

public class MessagesHandler implements MessageProcessor {

    private DatabaseHandler DatabaseHandler;
    private FilesHandler filesHandler;

    public MessagesHandler(world.skytale.database.DatabaseHandler databaseHandler, FilesHandler filesHandler) {
        DatabaseHandler = databaseHandler;
        this.filesHandler = filesHandler;
    }

    public world.skytale.database.DatabaseHandler getDatabaseHandler() {
        return DatabaseHandler;
    }


    public FilesHandler getFilesHandler() {
        return filesHandler;
    }



    @Override
    public void processIncoming(DownloadedMail downloadedMail) throws MessageProcessingException, IOException {

        MessageHeader messageHeader = MessageHeader.parseTitle(downloadedMail.getTitle());

        MessageProcessor processor= getMessageProcessorForType(messageHeader.getMessageType());
        processor.processIncoming(downloadedMail);

    }


    private MessageProcessor getMessageProcessorForType(String type) throws MessageProcessingException {
        switch (type)
        {
            case ChatMessageProcessor.TYPE_TAG:
               return getChatMessageProcessor();

        }

        throw new MessageProcessingException("Processing failed due to: unrecognized message Type");
    }


    public ChatMessageProcessor getChatMessageProcessor()
    {
        return new ChatMessageProcessor(this);
    }
}
