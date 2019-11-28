package world.skytale.database;

import world.skytale.model.AvaiableMessages.EncryptionKey;
import world.skytale.model.ID;

public interface EncryptionKeyHandler {
    EncryptionKey getEncryptionKey(ID senderID, long time) throws ItemNotFoundException;
    boolean addEncryptionKey(EncryptionKey encryptionKey);



}
