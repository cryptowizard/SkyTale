package world.skytale.cyphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

public final class AccountKey {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public static final  KeyPair generateKeyPair()
    {
        try {
            ECGenParameterSpec ecParamSpec = new ECGenParameterSpec("secp256k1");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH","SC");
            kpg.initialize(ecParamSpec);

            KeyPair keyPair=kpg.generateKeyPair();
            return keyPair;
        }catch (InvalidAlgorithmParameterException e)
        {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
