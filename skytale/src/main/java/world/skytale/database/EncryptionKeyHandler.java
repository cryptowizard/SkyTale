package world.skytale.database;

import world.skytale.model2.EncryptionKey;
import world.skytale.model2.ID;

public interface EncryptionKeyHandler {
    EncryptionKey getEncryptionKey(ID senderID, long time) throws ItemNotFoundException;
    boolean addEncryptionKey(EncryptionKey encryptionKey);



}
