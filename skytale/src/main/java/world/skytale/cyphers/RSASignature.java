package world.skytale.cyphers;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSASignature {

    public static final String FILE_EXTENSION = ".signature";
    public static byte [] signe(PrivateKey privateKey, byte [] message)
    {
        return new byte[100];
    }

    public static boolean veryfiSignature(PublicKey publicKey, byte [] message, byte [] signaturs)
    {
        return false;
    }
}
