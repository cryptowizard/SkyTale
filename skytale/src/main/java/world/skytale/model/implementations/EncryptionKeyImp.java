package world.skytale.model.implementations;


import androidx.annotation.NonNull;

import java.util.Date;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.skytale.model.EncryptionKey;

public class EncryptionKeyImp implements EncryptionKey {

    private final KeyID keyID;
    private final SecretKey key;
    private final long time;

    @NonNull
    @Override
    public long getTime() {
        return time;
    }

    public static final EncryptionKey generateNewKey(ID senderID, int keyType)
    {
        SecretKey secretKey = AES.generateNewKey();
        KeyID keyID = new KeyID(senderID, keyType);
        long time = new Date().getTime();
        return new EncryptionKeyImp(keyID, secretKey, time);
    }

    public EncryptionKeyImp(KeyID keyID, SecretKey key, long time) {
        this.keyID = keyID;
        this.key = key;
        this.time = time;
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
