package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import world.database.ItemNotFoundException;
import world.skytale.MessageProcessingException;
import world.skytale.messages.VeryfiedMessage;

public interface MessageProcessor {

    void processIncoming(VeryfiedMessage message) throws MessageProcessingException, IOException, InvalidKeyException, ItemNotFoundException, BadPaddingException, IllegalBlockSizeException , InvalidKeyException;


}
