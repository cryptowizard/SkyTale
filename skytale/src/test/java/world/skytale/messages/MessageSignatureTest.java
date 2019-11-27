package world.skytale.messages;

import org.junit.Test;

import java.security.KeyPair;
import java.util.Date;
import java.util.Random;

import world.skytale.cyphers.AccountKey;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model2.ID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MessageSignatureTest {

    @Test
    public void singMessageWithHeader() {

        Random random = new Random();

        byte [] messageBytes = new byte[200];
        random.nextBytes(messageBytes);
        long time = new Date().getTime();
        ID id = new ID(random.nextLong());
        MessageHeader messageHeader = new MessageHeader(ChatMessageBuilder.TYPE_TAG, id, time);
        KeyPair keyPair = AccountKey.generateKeyPair();


        byte [] signature = MessageSignature.singMessageWithHeader(messageHeader,messageBytes,keyPair.getPrivate());
        boolean result = MessageSignature.checkSingatureOfMessageandHeadder(messageHeader,messageBytes,signature,keyPair.getPublic());
        assertTrue(result);


        MessageHeader secondHeader = new MessageHeader(ChatMessageBuilder.TYPE_TAG, id, time+100);
        result = MessageSignature.checkSingatureOfMessageandHeadder(secondHeader,messageBytes,signature,keyPair.getPublic());
        assertFalse(result);


        random.nextBytes(signature);
        result = MessageSignature.checkSingatureOfMessageandHeadder(messageHeader,messageBytes,signature,keyPair.getPublic());
        assertFalse(result);

    }
}