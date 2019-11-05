package world.xfreemedia;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

        String mm = "";
        try {
         byte[] c = encrypt(aesKey, message.getBytes(ISO_8859_15));

         byte[] m = decrypt(aesKey, c);

         mm = new String(m, ISO_8859_15);
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }

        assertTrue(mm.equals(message));

    }


    @Test(expected = InvalidKeyException.class)
    public void testWrongKey () throws UnsupportedEncodingException, InvalidKeyException {
        SecretKey secondKey = AES.generateNewKey();
        byte [] m = SHORT_MESSAGE.getBytes(ISO_8859_15);

        byte [] c = AES.encrypt(aesKey,m);
        AES.decrypt(secondKey,c);
    }

    @Test(expected = InvalidKeyException.class)
    public void testWrongMessage () throws UnsupportedEncodingException, InvalidKeyException {
        byte [] m = SHORT_MESSAGE.getBytes(ISO_8859_15);
        AES.decrypt(aesKey,m);
    }



}