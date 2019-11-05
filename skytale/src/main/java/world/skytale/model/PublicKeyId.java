package world.skytale.model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Arrays;

import world.skytale.converters.LongConverter;
import world.skytale.cyphers.Sha256;

/**
 * PublicKeyId is used to convert long public key to unique 8 byte long value
 * The converting function is irreversible so it is impossible to generate public-key given its id value
 *
 */

public final class PublicKeyId implements Serializable, Comparable {

    static final  long serialVersionUID = 777L;

    private long id;


    public PublicKeyId(PublicKey publicKey)
    {
       this.id = generateID(publicKey);
    }

    public PublicKeyId(long publicKeyID)
    {
            this.id = publicKeyID;
    }


    public PublicKeyId(String publicKeyID)
    {
            this.id = LongConverter.fromBase32String(publicKeyID);
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

    private static long generateID(PublicKey publicKey)
    {
        byte [] publckKeyBytes = publicKey.getEncoded();

        byte [] hashedKey = Sha256.hash(publckKeyBytes);

        byte [] truncatedBytes = truncateBytes(hashedKey);

        long id = LongConverter.fromBytes(truncatedBytes);


        return Math.abs(id);

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
        else if(o instanceof PublicKeyId)
        {
            PublicKeyId tmp = (PublicKeyId) o;
            return (int) (this.toLong()-tmp.toLong());

        }
        else if(o instanceof PublicKey)
        {
            PublicKey tmp = (PublicKey) o;
            PublicKeyId publicKeyId = new PublicKeyId(tmp);
            return (int) (this.toLong()-publicKeyId.toLong());
        }
        return 1;
    }


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




}
