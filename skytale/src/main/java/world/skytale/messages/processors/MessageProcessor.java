package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;

import world.skytale.MessageProcessingException;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.database.ChatHandler;

public interface MessageProcessor {
    void processIncoming(VeryfiedMessage message) throws MessageProcessingException, IOException, ChatHandler.ChatNotFoundException, InvalidKeyException;


}
