package world.skytale;

import world.skytale.messages.MessageHeader;

public class MessageProcessingException extends Exception {

    private MessageHeader messageHeader;
    private final String sendersEmail;

    public MessageProcessingException(String sendersEmail) {
        this.sendersEmail = sendersEmail;
    }

    public MessageProcessingException(String message, Throwable cause, String sendersEmail) {
        super(message, cause);
        this.sendersEmail = sendersEmail;
    }

    public MessageProcessingException(MessageHeader messageHeader, String sendersEmail)
    {
        this.messageHeader = messageHeader;
        this.sendersEmail = sendersEmail;
    }

    public MessageProcessingException(MessageHeader messageHeader, String message, String sendersEmail)
    {
        super(message);
        this.messageHeader = messageHeader;
        this.sendersEmail = sendersEmail;
    }

    public MessageProcessingException(MessageHeader messageHeader, Throwable cause, String sendersEmail)
    {
        super(cause);
        this.messageHeader = messageHeader;
        this.sendersEmail = sendersEmail;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getSendersEmail() {
        return sendersEmail;
    }
}
