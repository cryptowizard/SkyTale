package world.skytale.skytale.converters;

import org.junit.Test;

import java.util.Random;

import world.skytale.converters.ByteConverter;

import static org.junit.Assert.assertTrue;


public class ByteConverterTest {

    @Test
    public void toStringTest()  {



        // Not unicode characters are not suportet
        String message = "Hello <3 @!@#$%^&*()<>?:{)";
        byte [] bytes = ByteConverter.fromString(message);

        String messageRecovered = ByteConverter.toString(bytes);
        assertTrue(message.equals(messageRecovered));



    }

    @Test
    public void fromBytesTest() {


        byte [] bytes = new byte[100];
        new Random().nextBytes(bytes);


        String encoded  = ByteConverter.toString(bytes);
        byte [] bytes1 = ByteConverter.fromString(encoded);
    }
}