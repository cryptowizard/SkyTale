package world.skytale;


import world.skytale.database.DatabaseHandler;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.MessageVerifier;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.messages.processors.ChatMessageProcessor;
import world.skytale.messages.processors.MessageProcessor;

public abstract class IncomingMessageProcessor {

    protected abstract DatabaseHandler getDatabaseHandler();


    /**
     * Method processes incoming mail and adds the result to the database
     * @param mail
     * @throws MessageProcessingException
     */
    public  void processIncoming(IncomingMail mail) throws MessageProcessingException
    {
        MessageHeader messageHeader = MessageHeader.parseTitle(mail.getTitle());
        try{
            VeryfiedMessage veryfiedMessage = getMessageVeryfier()
                    .veryfieMessage(messageHeader,mail.getAttachments());
            MessageProcessor messageProcessor = getMessageProcessor(veryfiedMessage.getMessageHeader().getMessageType());
            messageProcessor.processIncoming(veryfiedMessage);
        }
        catch (Exception exception)
        {
            throw new MessageProcessingException(messageHeader,exception,mail.getSendersEmail());
        }
    }

    private MessageVerifier getMessageVeryfier()
    {
        return new MessageVerifier(getDatabaseHandler().getTableContacts());
    }

    public MessageProcessor getMessageProcessor(String type)
    {
        switch (type)
        {
            case ChatMessageBuilder.TYPE_TAG:
                return getChatMessageHandler();

        }
        return null;
    }


    public ChatMessageProcessor getChatMessageHandler()
    {
        return new ChatMessageProcessor(getDatabaseHandler().getChatHandler(),getDatabaseHandler().getChatMessageHandler());
    }



}
