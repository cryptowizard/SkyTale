package world.skytale.cyphers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES {


    public static final int KEY_SIZE = 128;

    public static SecretKey generateNewKey()
    {
        try {
            return  KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }



    public static byte [] encrypt(SecretKey secretKey, byte [] m) throws InvalidKeyException {
        try {
           Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(m);
        }  catch (Exception e) {
        throw  new InvalidKeyException(e.getLocalizedMessage());
        }
    }

    public static byte [] decrypt(SecretKey secretKey, byte [] c) throws InvalidKeyException {
        try {
          Cipher  cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(c);
        } catch (Exception e) {
            throw  new InvalidKeyException(e.getLocalizedMessage());
        }


    }


}
