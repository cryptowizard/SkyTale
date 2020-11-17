package world.skytale.model;

import java.security.PrivateKey;

public interface Account {
     Contact getUserContact();
     PrivateKey getPrivateKey();
     ProfilePage getUserProfilePage();

     EncryptionKey getFriendsPostEncryptionKey();
     EncryptionKey getFollowersPostEncryptionKey();
}
