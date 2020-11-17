package world.database;

import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.KeyID;

public interface EncryptionKeyHandler {
    EncryptionKey getEncryptionKey(KeyID keyID) throws ItemNotFoundException;


    EncryptionKey getEncryptionKeyWithTheLowestType(ID senderID) throws ItemNotFoundException;
    boolean addEncryptionKey(EncryptionKey encryptionKey);


}
