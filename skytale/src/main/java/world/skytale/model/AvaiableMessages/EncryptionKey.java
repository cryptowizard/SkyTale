package world.skytale.model.AvaiableMessages;

import javax.crypto.SecretKey;

import world.skytale.model.ID;

public interface EncryptionKey {
    ID getSenderID();
    long getTime();
    SecretKey getKey();
}
