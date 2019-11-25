package world.skytale.model2;

import javax.crypto.SecretKey;

public interface EncryptionKey {
    ID getSenderID();
    long getTime();
    SecretKey getKey();
}
