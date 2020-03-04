package world.database;

import world.skytale.model.sendable.EncryptionKey;
import world.skytale.model.ID;

public interface EncryptionKeyHandler {
    EncryptionKey getEncryptionKey(ID senderID, long time) throws ItemNotFoundException;


    boolean addEncryptionKey(EncryptionKey encryptionKey);
}
