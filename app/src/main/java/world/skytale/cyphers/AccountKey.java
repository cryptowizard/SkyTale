package world.skytale.cyphers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public final class AccountKey {

    public static final int KEY_SIZE = 4096;

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

}
