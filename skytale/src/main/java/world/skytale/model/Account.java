package world.skytale.model;

import java.security.PrivateKey;

import world.skytale.model.sendable.EncryptionKey;

public interface Account {
     Contact getUserContact();
     PrivateKey getPrivateKey();
     ProfilePage getUserProfilePage();

     EncryptionKey getFriendsPostEncryptionKey();
     EncryptionKey getFollowersPostEncryptionKey();
}
