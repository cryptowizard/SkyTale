package world.skytale.converters;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


/**
 *  PublicKeyConverter methods are used to store and send public either as byte or String
 *
 */
public class PublickKeyConverter {


    /**
     *
     * @param publicKey
     * @return  X509EncodedKeySpec encdoed to ISO-8859-15 String
     *
     * @see X509EncodedKeySpec
     *
     */
    public static String toString(PublicKey publicKey)
    {
        byte [] bytes = toBytes(publicKey);
        return  ByteConverter.toString(bytes);
    }


    /**
     * @param publicKey
     * @return X.509 encoded Public Key
     * @see X509EncodedKeySpec
     */
    public static byte [] toBytes(PublicKey publicKey)
    {
        return publicKey.getEncoded();
    }

    /**
     * @param encodedString ISO-8859-15 encoded bytes with X.509 encoded Public Key
     * @return  Public key from spec
     * @throws InvalidKeySpecException
     */
    public static PublicKey fromString(String encodedString) throws InvalidKeySpecException {
        byte [] bytes = ByteConverter.fromString(encodedString);
        return fromBytes(bytes);
    }

    /**
     * Creates a new X509EncodedKeySpec with the given encoded key.
     *
     * @param encodedPublicKey the key, which is assumed to be
     * encoded according to the X.509 standard. The contents of the
     * array are copied to protect against subsequent modification.
     * @exception NullPointerException if {@code encodedKey}
     * is null.
     *
     * @see X509EncodedKeySpec
     */
    public static PublicKey fromBytes(byte [] encodedPublicKey) throws InvalidKeySpecException {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedPublicKey));
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e.getMessage());
        }

    }
}
