package world.skytale.cyphers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {

    private static final MessageDigest MESSAGE_DIGEST = getMessageDigest();

    private static final MessageDigest getMessageDigest()
    {
        try {
           return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public static byte [] hash(byte [] input)
    {
        return MESSAGE_DIGEST .digest(input);
    }
}
