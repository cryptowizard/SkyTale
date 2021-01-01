package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import javax.crypto.SecretKey;

import world.skytale.converters.SecretKeyConventer;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.KeyID;

public class EncryptionKeyDao implements EncryptionKey {

    public static final int TYPE_DEFAULT = 100;

    private final int keyType;
    private final long senderID;

    private final SecretKey secretKey;




    public EncryptionKeyDao( long senderID,int keyType,SecretKey secretKey) {
        this.keyType = keyType;
        this.senderID = senderID;
        this.secretKey =secretKey;
    }

    public EncryptionKeyDao(long senderID, SecretKey secretKey)
    {
        this(senderID,TYPE_DEFAULT,secretKey);
    }

    public EncryptionKeyDao(ID senderID, SecretKey secretKey)
    {
        this(senderID.toLong(), secretKey);
    }

    public EncryptionKeyDao(long senderID, int keyType, String secretKeyString)
    {
        this.keyType = keyType;
        this.senderID = senderID;
        this.secretKey = SecretKeyConventer.fromString(secretKeyString);
    }

    public EncryptionKeyDao(EncryptionKey encryptionKey)
    {
        this.keyType = encryptionKey.getKeyID().getKeyType();
        this.senderID = encryptionKey.getKeyID().getSenderID().toLong();
        this.secretKey = encryptionKey.getKey();

    }

    public EncryptionKeyDao(ID senderID, int type, SecretKey secretKey) {
        this.keyType = type;
        this.senderID = senderID.toLong();
        this.secretKey =secretKey;
    }


    @NonNull
    @Override
    public KeyID getKeyID() {
        return new KeyID(senderID, keyType);
    }

    @NonNull
    @Override
    public SecretKey getKey() {
        return secretKey;
    }


    public int getKeyType() {
        return keyType;
    }

    public long getSenderID() {
        return senderID;
    }

    public String getSecretKeyString()
    {
        return SecretKeyConventer.toString(secretKey);
    }
}
