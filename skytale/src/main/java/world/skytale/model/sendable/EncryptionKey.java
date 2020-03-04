package world.skytale.model.sendable;

import javax.crypto.SecretKey;

public interface EncryptionKey extends Sendable{
    SecretKey getKey();
}
