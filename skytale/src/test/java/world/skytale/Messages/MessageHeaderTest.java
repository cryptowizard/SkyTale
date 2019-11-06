package world.skytale.Messages;

import org.junit.Test;

import java.util.Date;
import java.util.Random;

import world.skytale.Messages.processors.ChatMessageProcessor;
import world.skytale.model.PublicKeyId;

import static org.junit.Assert.assertEquals;

public class MessageHeaderTest {

    @Test
    public void parseTitle() throws MessageProcessingException {

        long time = new Date().getTime();
        PublicKeyId ID = new PublicKeyId(new Random().nextLong());
        String type = ChatMessageProcessor.TYPE_TAG;

       MessageHeader header = new MessageHeader(type,ID,time);

       String title = header.makeTitle();

       MessageHeader header1 = MessageHeader.parseTitle(title);

        assertEquals(header1.getMessageType(),type);
        assertEquals(header1.getSenderID().toLong(),ID.toLong());
        assertEquals(header1.getTime(), time);


    }

    @Test(expected = MessageProcessingException.class)
    public void parseWrongTitle() throws MessageProcessingException {
            String title = ";ffff;1111;";

            MessageHeader.parseTitle(title);
    }

}