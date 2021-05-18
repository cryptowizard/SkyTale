package world.skytale.model.proto;

import androidx.annotation.NonNull;

import javax.crypto.SecretKey;

import world.skytale.converters.ByteConverter;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.message.Messages;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.KeyID;

public class EncryptionKeyProto implements EncryptionKey {

    private final Messages.EncryptionKey protoMessage;
    private final long senderID;


    public EncryptionKeyProto(Messages.EncryptionKey protoMessage, long senderID) {
        this.protoMessage = protoMessage;
        this.senderID = senderID;
    }


    public static Messages.EncryptionKey toProtoMessage(EncryptionKey encryptionKey)
    {

        Messages.EncryptionKey protoEncryptionKey = Messages.EncryptionKey.newBuilder()
                .setEncryptionKeyType(encryptionKey.getKeyID().getKeyType())
                .setSecretKeyBytes(ByteConverter.toByteString(encryptionKey.getKey().getEncoded()))
                .setTime(encryptionKey.getTime())
                .build();

        return protoEncryptionKey;
    }

    @NonNull
    @Override
    public KeyID getKeyID() {
        return new KeyID(senderID, protoMessage.getEncryptionKeyType());
    }

    @NonNull
    @Override
    public long getTime() {
        return protoMessage.getTime();
    }

    @Override
    public SecretKey getKey() {
        byte [] keyBytes = protoMessage.getSecretKeyBytes().toByteArray();
        return SecretKeyConventer.fromBytes(keyBytes);
    }


}
