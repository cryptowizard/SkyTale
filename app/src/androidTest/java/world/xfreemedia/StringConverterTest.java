package world.xfreemedia;

import org.junit.Test;

import static world.xfreemedia.converters.StringConverter.toBytes;

public class StringConverterTest {

    @Test
    public void toBytesTest() {

        String message = "Hello this is me MSD";
        byte [] bytes = toBytes(message);


    }

    @Test
    public void fromBytesTest() {
    }
}