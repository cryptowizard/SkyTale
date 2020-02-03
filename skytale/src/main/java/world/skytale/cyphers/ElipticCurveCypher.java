package world.skytale.cyphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ElipticCurveCypher {

    public static final String SPEC = "secp256k1";
    public static final String ALGO = "SHA256withECDSA";
    public static final String PROVIDER = "SC";
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public static KeyPair generateKeyPair()
    {
        try {
            ECGenParameterSpec ecParamSpec = new ECGenParameterSpec("secp256k1");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH","SC");
            kpg.initialize(ecParamSpec);

            KeyPair keyPair=kpg.generateKeyPair();
            return keyPair;
        }catch (InvalidAlgorithmParameterException exception)
        {
            exception.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte [] encrypt(PublicKey publicKey, byte [] message)
    {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("ECIES",PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            return cipher.doFinal(message);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();

            /**
             * @ToDo Create a stream cypher for larger message sizes
             *
             */
            return null;
        }
        return null;
    }

    public static byte [] decrypt(PrivateKey privateKey, byte [] encryptedMessage) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("ECIES",PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encryptedMessage);
    }

}
