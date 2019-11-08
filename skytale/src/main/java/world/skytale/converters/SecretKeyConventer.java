package world.skytale.converters;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeyConventer {


    public static byte [] toBytes(SecretKey secretKey)
    {
       return secretKey.getEncoded();
    }


    public static SecretKey fromBytes(byte []bytes)
    {
        SecretKey originalKey = new SecretKeySpec(bytes,0, 16, "AES");
        return originalKey;
    }

    public static String toString(SecretKey secretKey)
    {
        byte [] secretKeyBytes = secretKey.getEncoded();
        return ByteConverter.toString(secretKeyBytes);
    }

    public static SecretKey fromString(String encodedKey)
    {
        byte [] secretKeyBytes = ByteConverter.fromString(encodedKey);
        return fromBytes(secretKeyBytes);
    }
}
