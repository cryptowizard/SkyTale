package world.skytale.model.implementations;


import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;

import world.skytale.cyphers.AES;
import world.skytale.model.ID;
import world.skytale.model.MessageID;
import world.skytale.model.sendable.EncryptionKey;

public class EncryptionKeyImp implements EncryptionKey {

    private  final MessageID messageID;
    private final SecretKey key;

    public static final EncryptionKey generateNewKey(ID senderID)
    {
        // random is added to avoid creating two keys with the same ID
        long time = new Date().getTime() + new Random().nextInt(1000*60*60);
        SecretKey secretKey = AES.generateNewKey();

        return new EncryptionKeyImp(new MessageID(senderID,time),secretKey);
    }

    public EncryptionKeyImp(MessageID messageID, SecretKey key) {
        this.messageID = messageID;
        this.key = key;
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }

    @Override
    public SecretKey getKey() {
        return key;
    }
}
