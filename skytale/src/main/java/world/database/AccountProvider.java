package world.database;

import world.skytale.model.Account;
import world.skytale.model.sendable.EncryptionKey;

/**
 * Account provider object is used to retrieve current users Account and make changes to it
 */
public interface AccountProvider {
    Account getCurrentAccount();


    /**
     *
     * @return true if new encryption Keys where generated and saved correctly
     */
    boolean updatePostEncryptionKeys(EncryptionKey friendsPostEncryptionKey, EncryptionKey followersEncryptionKey);
}
