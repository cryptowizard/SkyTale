package world.skytale.converters;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class LongConverterTest {


    public Random random = new Random();


    private long getRandomLong()
    {
        return random.nextLong();
    }


    @Test
    public void toBytes() {

        for(int i=0;i<10;i++)
        {
            long number = getRandomLong();

            byte [] bytes = LongConverter.toBytes(number);
            long fromBytes = LongConverter.fromBytes(bytes);

            assertEquals(number,fromBytes);
        }

    }


    @Test
    public void toBase32String() {

        for(int i=0;i<10;i++)
        {
            long number = getRandomLong();

            String  encoded = LongConverter.toBase32String(number);
            long decoded = LongConverter.fromBase32String(encoded);


            assertEquals(number,decoded);
        }

    }


}