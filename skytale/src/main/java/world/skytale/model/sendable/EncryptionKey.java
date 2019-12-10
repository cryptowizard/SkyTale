package world.skytale.model.sendable;

import javax.crypto.SecretKey;

import world.skytale.model.ID;

public interface EncryptionKey extends Sendable{
    ID getSenderID();
    long getTime();
    SecretKey getKey();
}
