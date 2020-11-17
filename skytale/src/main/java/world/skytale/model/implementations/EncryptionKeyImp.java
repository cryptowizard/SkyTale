package world.skytale.model.implementations;


import androidx.annotation.NonNull;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.skytale.model.EncryptionKey;

public class EncryptionKeyImp implements EncryptionKey {

    private final KeyID keyID;
    private final SecretKey key;

    public static final EncryptionKey generateNewKey(ID senderID, int keyType)
    {
        SecretKey secretKey = AES.generateNewKey();
        KeyID keyID = new KeyID(senderID, keyType);
        return new EncryptionKeyImp(keyID, secretKey);
    }

    public static EncryptionKey generateNewKey(ID senderID)
    {
        return generateNewKey(senderID, 777);
    }

    public EncryptionKeyImp(KeyID keyID, SecretKey key) {
        this.keyID = keyID;
        this.key = key;
    }


    @NonNull
    @Override
    public KeyID getKeyID() {
        return keyID;
    }

    @Override
    public SecretKey getKey() {
        return key;
    }


}
