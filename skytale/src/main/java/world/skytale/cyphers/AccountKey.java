package world.skytale.cyphers;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public final class AccountKey {

    public static final int KEY_SIZE = 512;

    public static final  KeyPair generateKeyPair()
    {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(KEY_SIZE);
            return keyPairGenerator.genKeyPair();
        }
        catch (Exception e)
        {
           throw  new RuntimeException(e.getMessage());
        }
    }


    public static PublicKey fromBytes(byte [] bytes)
    {
        try {
            return      KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
