package world.mockedhandlers;

import org.junit.Test;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.EncryptionKeyImp;
import world.skytale.model.implementations.ID;

import static org.junit.Assert.*;

public class MockedEncryptionKeyHandlerTest {

    EncryptionKeyHandler encryptionKeyHandler = new MockedEncryptionKeyHandler();

    @Test
    public void getEncryptionKey() throws ItemNotFoundException {
        EncryptionKey encryptionKey = makeRandomKey();
        encryptionKeyHandler.addEncryptionKey(encryptionKey);

        EncryptionKey encryptionKey1 = encryptionKeyHandler.getEncryptionKey(encryptionKey.getKeyID());
        checkIfEqual(encryptionKey, encryptionKey1);
    }

    @Test(expected = ItemNotFoundException.class)
    public void WhenKeyIsNotInTableHandlerThrowsException() throws ItemNotFoundException {
        EncryptionKey encryptionKey = makeRandomKey();
        encryptionKeyHandler.getEncryptionKey(encryptionKey.getKeyID());
    }

    @Test
    public void getEncryptionKeyWithTheLowestType() throws ItemNotFoundException {
        ID senderID = ID.generateRandomID();
        EncryptionKey encryptionKey = EncryptionKeyImp.generateNewKey(senderID, 1);
        EncryptionKey encryptionKey1 =  EncryptionKeyImp.generateNewKey(senderID, -100);

        encryptionKeyHandler.addEncryptionKey(encryptionKey);
        encryptionKeyHandler.addEncryptionKey(encryptionKey1);

        EncryptionKey encryptionKey2 = encryptionKeyHandler.getEncryptionKeyWithTheLowestType(encryptionKey.getKeyID().getSenderID());
        checkIfEqual(encryptionKey2, encryptionKey1);
    }

    @Test
    public void addEncryptionKey() {

        EncryptionKey encryptionKey = makeRandomKey();

       boolean result =  encryptionKeyHandler.addEncryptionKey(encryptionKey);
       assertTrue(result);
    }

    private EncryptionKey makeRandomKey()
    {
        ID senderID = ID.generateRandomID();
        EncryptionKey encryptionKey = EncryptionKeyImp.generateNewKey(senderID, 1);
        return encryptionKey;
    }

    public void checkIfEqual(EncryptionKey encryptionKey1, EncryptionKey encryptionKey2)
    {
        assertEquals(encryptionKey1.getKeyID().getKeyType(), encryptionKey2.getKeyID().getKeyType());
        assertEquals(encryptionKey1.getKeyID().getSenderID(), encryptionKey2.getKeyID().getSenderID());
        assertEquals(SecretKeyConventer.toString(encryptionKey1.getKey()),SecretKeyConventer.toString(encryptionKey2.getKey()));
        assertEquals(encryptionKey1.getTime(), encryptionKey2.getTime());
    }
}