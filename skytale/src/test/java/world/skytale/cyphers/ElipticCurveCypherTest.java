package world.skytale.cyphers;

import org.junit.Test;

import java.security.KeyPair;

import static junit.framework.TestCase.assertTrue;

public class ElipticCurveCypherTest {

    @Test
    public void generateKeyPair() {

        KeyPair keyPair= ElipticCurveCypher.generateKeyPair();
        assertTrue(keyPair!=null);
    }
}