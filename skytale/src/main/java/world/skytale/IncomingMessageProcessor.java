package world.skytale;


import world.skytale.database.DatabaseHandler;
import world.skytale.database.FilesHandler;
import world.skytale.messages.IncomingMail;
import world.skytale.messages.MessageHeader;

public abstract class IncomingMessageProcessor {

    protected abstract FilesHandler getFilesHandler();
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
            VeryfiedMessage veryfiedMessage = getMessageVeryfier().veryfieMessage(messageHeader,mail.getAttachments());
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






}
