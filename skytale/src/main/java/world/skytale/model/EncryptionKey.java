package world.skytale.model;

import androidx.annotation.NonNull;

import javax.crypto.SecretKey;

import world.skytale.model.implementations.KeyID;

public interface EncryptionKey{
    /**
     * Key type uniquelly identifies encryption key
     * keys with the lower type should be send to smaller groups of contact and keyType with a higher type is expected to be shared with wider group of contacts
     * For example key type for followers should be higher than key type for friends
     *
     * If the key with the same type is Send it means the key is updated
     * @return
     */
    @NonNull
    KeyID getKeyID();

    @NonNull
    long getTime();


    @NonNull
    SecretKey getKey();
}
