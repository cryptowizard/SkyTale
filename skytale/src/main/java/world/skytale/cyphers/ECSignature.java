package world.skytale.cyphers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;

public class ECSignature {

    public static final String ALGO = "SHA256withECDSA";

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public static byte [] sign(PrivateKey privateKey, byte [] message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        java.security.Signature privateSignature = java.security.Signature.getInstance(ALGO);
        privateSignature.initSign(privateKey);
        privateSignature.update(message);

        byte[] signature = privateSignature.sign();

        return signature;
    }

    public static boolean veryfiSignature(PublicKey publicKey, byte [] message, byte [] signature)
    {
        try {
            java.security.Signature publicSignature = java.security.Signature.getInstance(ALGO);
            publicSignature.initVerify(publicKey);
            publicSignature.update(message);


            return publicSignature.verify(signature);
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
