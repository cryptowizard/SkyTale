package world.skytale.skytale.cyphers;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;

import static junit.framework.TestCase.assertTrue;
import static world.skytale.cyphers.AES.decrypt;
import static world.skytale.cyphers.AES.encrypt;


public class AESTest {


    public static final String ISO_8859_15 = "ISO-8859-15";
    public static final String SHORT_MESSAGE = "Yo<3";
    public static final  String MEDIUM_MESSAGE = "SkyTale world is the future";


    SecretKey aesKey;

    private static byte [] randomIvVector()
    {
        byte [] randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
        return randomBytes;
    }

    @Before
    public void generateNewKey() throws NoSuchAlgorithmException {
        aesKey = AES.generateNewKey();
    }


    @Test
    public void testShortMessage()
    {
        testForMessage(SHORT_MESSAGE);
    }

    @Test
    public void testMediumMessage()
    {
        testForMessage(MEDIUM_MESSAGE);
    }

    @Test
    public void testLongMessage()
    {
        String longMessage = MEDIUM_MESSAGE;
        for(int i=0;i<10;i++)
        {
            longMessage+=longMessage;
        }

        testForMessage(longMessage);
    }

    private void testForMessage(String message) {
        byte  [] ivVector = randomIvVector();

        try {
         byte[] c = encrypt(aesKey, message.getBytes(ISO_8859_15),ivVector);

         byte[] m = decrypt(aesKey, c,ivVector);

        String encryptedMessage = new String(m, ISO_8859_15);
            assertTrue(encryptedMessage.equals(message));
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
    }


    @Test(expected = InvalidKeyException.class)
    public void testWrongKey () throws UnsupportedEncodingException, InvalidKeyException {
        byte  [] ivVector = randomIvVector();
        SecretKey secondKey = AES.generateNewKey();
        byte [] m = SHORT_MESSAGE.getBytes(ISO_8859_15);

        byte [] c = AES.encrypt(aesKey,m,ivVector);
        AES.decrypt(secondKey,c,ivVector);
    }

    @Test(expected = InvalidKeyException.class)
    public void testWrongMessage () throws UnsupportedEncodingException, InvalidKeyException {
        byte  [] ivVector = randomIvVector();
        byte [] m = SHORT_MESSAGE.getBytes(ISO_8859_15);
        AES.decrypt(aesKey,m,ivVector);
    }



}