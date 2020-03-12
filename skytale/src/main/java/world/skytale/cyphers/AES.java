package world.skytale.cyphers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import world.skytale.model.MessageID;

import static world.skytale.cyphers.IvVectorGenerator.generateIvVector;

public class AES {


    public static final int KEY_SIZE = 128;

    public static SecretKey generateNewKey()
    {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(KEY_SIZE);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }


    public static byte [] encrypt(SecretKey secretKey, byte [] m, MessageID messageID) throws InvalidKeyException {
            return encrypt(secretKey,m,generateIvVector(messageID));
    }


        public static byte [] encrypt(SecretKey secretKey, byte [] m, byte [] ivVector) throws InvalidKeyException {
        try {
           Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
           IvParameterSpec ivParameterSpec = new IvParameterSpec(ivVector);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
            return cipher.doFinal(m);
        }  catch (Exception e) {
        throw  new InvalidKeyException(e.getLocalizedMessage());
        }
    }


    public static byte [] decrypt(SecretKey secretKey, byte [] c, MessageID messageID) throws InvalidKeyException
    {
        return decrypt(secretKey,c,generateIvVector(messageID));
    }

        public static byte [] decrypt(SecretKey secretKey, byte [] c, byte [] ivVector) throws InvalidKeyException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivVector);
          Cipher  cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
            return cipher.doFinal(c);
        } catch (Exception e) {
            throw  new InvalidKeyException(e.getLocalizedMessage());
        }


    }


}
