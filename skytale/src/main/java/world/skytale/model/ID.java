package world.skytale.model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Random;

import world.skytale.converters.LongConverter;
import world.skytale.cyphers.Sha256;


/**
 *  IDs are long values used to uniquely identify object of the given contactType in database
 *  64 bit positive long values are enough due to decentralized nature of the network
 */
public class ID implements Serializable, Comparable {

    static final  long serialVersionUID = 777L;

    private  long id;


    /**
     *  Random Id that can be used for fileNames and
     * @return id made from random positive long
     */
    public static ID generateRandomID()
    {
        long id = new Random().nextLong();
        return new ID(id);
    }

    public ID(long id)
    {
            this.id = id;
    }



    public ID(String id)
    {
            this.id = LongConverter.fromBase32String(id);
    }
    

    /**
     * @return String with the long value saved with base 32
     * Base 32 is used so the returned string is shorter than base 10 or 16, but still easily readable and does not use any special characters
     * So it can be send in E-mail title without changing value due to different encoding used by the service provider
     */

    public String toString()
    {
        return LongConverter.toBase32String(id);
    }

    public long toLong()
    {
        return id;
    }

    private static byte [] truncateBytes(byte [] bytes)
    {
        return Arrays.copyOf(bytes,8);
    }


    @Override
    public int compareTo(Object o) {

        if(o instanceof Long)
        {
            Long tmp = (Long) o;
            return -tmp.compareTo(id);

        }
        else if(o instanceof String)
        {
            String tmp = (String) o;
            return this.toString().compareTo(tmp);
        }
        else if(o instanceof ID)
        {
            ID tmp = (ID) o;
            return (int) (this.toLong()-tmp.toLong());

        }
        else if(o instanceof PublicKey)
        {
            PublicKey tmp = (PublicKey) o;
            ID uniqueID = PublicKeyID.makeID(tmp);
            return (int) (this.toLong()- uniqueID.toLong());
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
        {
            return false;
        }
        else
        {
            return (this.compareTo(obj)==0);

        }
    }

    /**
     * id is used to convert long public key to unique 8 byte long value
     * The converting function is irreversible so it is impossible to generate public-key given its id value
     *
     */
public static final class PublicKeyID {


        public static ID makeID(PublicKey publicKey)
        {
            long id =  generateID(publicKey);
            return new ID(id);
        }

    private static long generateID(PublicKey publicKey)
    {
        byte [] publickKeyBytes = publicKey.getEncoded();

        byte [] hashedKey = Sha256.hash(publickKeyBytes);

        byte [] truncatedBytes = truncateBytes(hashedKey);

        long id = LongConverter.fromBytes(truncatedBytes);


        return id;

    }
}

}
