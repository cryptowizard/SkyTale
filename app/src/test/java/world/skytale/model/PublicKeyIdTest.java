package world.skytale.model;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.security.PublicKey;

import world.skytale.cyphers.AccountKey;

public class PublicKeyIdTest {

    public static final int LENGTH = 20;
    static final PublicKey [] randomKeys = getRandomKeys();


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
            PublicKeyId id = new PublicKeyId(randomKeys[i]);

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

        }

    }

    @Test
    public void toString1() {

        for(int i=0;i<LENGTH;i++)
        {
            PublicKeyId id = new PublicKeyId(randomKeys[i]);


            String string = id.toString();
            PublicKeyId idFromString = new PublicKeyId(string);

            assertEquals(id.toLong(),idFromString.toLong());


        }

    }

    @Test
    public void generateID() {


    }


}

