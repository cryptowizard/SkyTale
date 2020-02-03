package world;

import android.util.Log;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import world.skytale.converters.PublickKeyConverter;
import world.skytale.cyphers.ElipticCurveCypher;
import world.skytale.cyphers.ECSignature;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EliticCurveTest {
    @Test
    public void generateKeyPair() {
        KeyPair keyPair= ElipticCurveCypher.generateKeyPair();
        assertTrue(keyPair!=null);
    }

    @Test
    public void xd() throws InvalidKeySpecException {
        KeyPair keyPair= ElipticCurveCypher.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();

        byte [] publicKeyBytes = PublickKeyConverter.toBytes(publicKey);
        PublicKey publicKey1 = PublickKeyConverter.fromBytes(publicKeyBytes);


       Log.i("algorithm", publicKey1.getAlgorithm());
        Log.i("algorithm",publicKey1.toString());
        Log.i("algorithm",publicKey.toString());
        Log.i("algorithm",publicKeyBytes.length+"");

        PrivateKey privateKey = keyPair.getPrivate();
        Log.i("algorithm",privateKey.getAlgorithm());
    }

    @Test
    public void signatureTest() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPair keyPair= ElipticCurveCypher.generateKeyPair();

        byte [] messageBytes = "Hello".getBytes();

        byte [] signature = ECSignature.sign(keyPair.getPrivate(),messageBytes);

        Log.i("algorithm","size :"+signature.length);
        boolean result = ECSignature.veryfiSignature(keyPair.getPublic(),messageBytes,signature);

        assertTrue(result);
    }

    @Test
    public void encryptionTest() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        KeyPair keyPair= ElipticCurveCypher.generateKeyPair();

        byte [] key = "Hellow".getBytes();

        byte [] c = ElipticCurveCypher.encrypt(keyPair.getPublic(),key);
        byte [] m = ElipticCurveCypher.decrypt(keyPair.getPrivate(),c);

        assertEquals(new String(m),new String(key));

        Log.i("algorithm", new String(m));


    }


}
