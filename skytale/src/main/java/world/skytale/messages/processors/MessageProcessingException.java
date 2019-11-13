package world.skytale.messages.processors;

import world.skytale.messages.MessageHeader;

public class MessageProcessingException extends Exception {

    public MessageHeader messageHeader;
    Exception couse;

    public MessageProcessingException(String message)
    {
        super(message);
    }
}
