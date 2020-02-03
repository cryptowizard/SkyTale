package world.skytale;


import world.database.DatabaseHandler;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.MessageVerifier;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.messages.builders.FriendRequestBuilder;
import world.skytale.messages.builders.FriendRequestResponseBuilder;
import world.skytale.messages.builders.PostMessageBuilder;
import world.skytale.messages.processors.ChatMessageProcessor;
import world.skytale.messages.processors.FriendRequestProcessor;
import world.skytale.messages.processors.FriendRequestResponseProcessor;
import world.skytale.messages.processors.MessageProcessor;
import world.skytale.messages.processors.PostMessageProcessor;

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
                    .veryfieMessage(messageHeader,mail.getAttachments(),mail.getSendersEmail());
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
                return getChatMessageProcessor();
            case FriendRequestBuilder
                   .TYPE_TAG:
                return getFriendRequestProcessor();
            case FriendRequestResponseBuilder
                    .TYPE_TAG:
                return new FriendRequestResponseProcessor(getDatabaseHandler());
            case  PostMessageBuilder.TYPE_TAG:
                return new PostMessageProcessor(getDatabaseHandler().getPostHandler());



        }
        return null;
    }


    public ChatMessageProcessor getChatMessageProcessor()
    {
        return new ChatMessageProcessor(getDatabaseHandler().getChatHandler(),getDatabaseHandler().getChatMessageHandler());
    }

public FriendRequestProcessor getFriendRequestProcessor()
{
    return new FriendRequestProcessor(getDatabaseHandler());
}




}
