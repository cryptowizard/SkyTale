package world.skytale.model.proto;

import androidx.annotation.NonNull;

import javax.crypto.SecretKey;

import world.skytale.converters.ByteConverter;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.message.Messages;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.EncryptionKey;

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
                .setTime(encryptionKey.getMessageID().getTime())
                .setSecretKeyBytes(ByteConverter.toByteString(encryptionKey.getKey().getEncoded()))
                .build();

        return protoEncryptionKey;
    }

    @Override
    public SecretKey getKey() {
        byte [] keyBytes = protoMessage.getSecretKeyBytes().toByteArray();
        return SecretKeyConventer.fromBytes(keyBytes);
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return new MessageID(senderID, protoMessage.getTime());
    }
}
