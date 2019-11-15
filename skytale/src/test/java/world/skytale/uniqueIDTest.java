package world.skytale;

import org.junit.Test;

import java.security.PublicKey;
import java.util.HashMap;

import world.skytale.cyphers.AccountKey;
import world.skytale.model2.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class uniqueIDTest {

    public static final int LENGTH = 2;
    static  PublicKey [] randomKeys = getRandomKeys();


    public static   PublicKey [] getRandomKeys (){

        PublicKey [] randomKeys = new PublicKey[LENGTH];
        for(int i=0;i<LENGTH;i++)
        {
            randomKeys[i] = generatePublicKey();
        }
        return randomKeys;
    }



    public static PublicKey generatePublicKey()
    {
        return AccountKey.generateKeyPair().getPublic();
    }


    @Test
    public void compareTo() {
        for(int i=0;i<LENGTH;i++)
        {
            ID id = ID.PublicKeyID.makeID(randomKeys[i]);

            System.out.println(id.toString());
            System.out.println(id.toLong());
            String normal = ""+id.toLong();
            String base32 = id.toString();
            System.out.println(normal.length());
            System.out.println(base32.length());
            boolean result = id.equals(randomKeys[i]);
          //
            assertEquals(id,randomKeys[i]);
            assertEquals(id,id.toLong());


            assertEquals(id,id.toString());

            assertFalse(id.equals("Hello"));

        }

    }

    @Test
    public void toString1() {

        for(int i=0;i<LENGTH;i++)
        {
            ID id = ID.PublicKeyID.makeID(randomKeys[i]);


            String string = id.toString();
            ID idFromString = new ID(string);

            assertEquals(id.toLong(),idFromString.toLong());


        }

    }

    @Test
    public void generateID() {

        String wrongString = "Hmm1421412";
        ID uniqueID = new ID(wrongString);
       System.out.println(uniqueID.toLong());
    }


    @Test
    public void randomIDTest()
    {
            int length = 1000*1000;

            HashMap <Long,Integer> map = new HashMap<Long,Integer>();
            for (int i = 0; i < length; i++) {
                map.put(ID.generateRandomID().toLong(), 1);
                if (i % (length / 20) == 0) {
                    System.out.println(i / (length / 100) + "%  "+"size : " + map.size());

                }
            }
            int size =  map.size();
            System.out.println(length);
            System.out.println(size);

            assertEquals(size,length);
    }
}

