package world.skytale.messages;

import org.junit.Test;

import java.util.Date;
import java.util.Random;

import world.skytale.MessageProcessingException;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model.implementations.ID;

import static org.junit.Assert.assertEquals;

public class MessageHeaderTest {

    @Test
    public void parseTitle() throws MessageProcessingException {

        long time = new Date().getTime();
        ID ID = new ID(new Random().nextLong());
        String type = ChatMessageBuilder.TYPE_TAG;

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