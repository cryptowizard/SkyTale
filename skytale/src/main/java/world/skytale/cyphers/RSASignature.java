package world.skytale.cyphers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class RSASignature {

    public static byte [] sign(PrivateKey privateKey, byte [] message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(message);

        byte[] signature = privateSignature.sign();

        return signature;
    }

    public static boolean veryfiSignature(PublicKey publicKey, byte [] message, byte [] signature)
    {
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
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
