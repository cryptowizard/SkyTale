package world.mockedhandlers;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.model.Account;
import world.skytale.model.Contact;
import world.skytale.model.EncryptionKey;
import world.skytale.model.ProfilePage;
import world.skytale.model.implementations.ContactImp;
import world.skytale.model.implementations.EncryptionKeyImp;
import world.skytale.model.implementations.ID;

public class AccountImp implements Account {

    private final Contact userContact;
    private ProfilePage userProfilePage;
    private final  EncryptionKey friendsPostEncryptionKey;
    private final EncryptionKey followersPostEncryptionKey;
    private final PrivateKey privateKey;


    public AccountImp(Contact userContact, EncryptionKey friendsPostEncryptionKey, EncryptionKey followersPostEncryptionKey, PrivateKey privateKey) {
        this.userContact = userContact;
        this.friendsPostEncryptionKey = friendsPostEncryptionKey;
        this.followersPostEncryptionKey = followersPostEncryptionKey;
        this.privateKey = privateKey;
    }

    private AccountImp(KeyPair keyPair, String email)
    {
        this.privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        ID id = ID.PublicKeyID.makeID(publicKey);
        this.userContact = new ContactImp(id,publicKey,email);
        this.followersPostEncryptionKey = EncryptionKeyImp.generateNewKey(id,1);
        this.friendsPostEncryptionKey  = EncryptionKeyImp.generateNewKey(id,2);

    }


    public static AccountImp makeNewAccount(String email)
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
        AccountImp account = new AccountImp(keyPair,email);
        return account;
    }


    @Override
    public Contact getUserContact() {
        return userContact;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public ProfilePage getUserProfilePage() {
        return userProfilePage;
    }

    public void setUserProfilePage(ProfilePage userProfilePage) {
        this.userProfilePage = userProfilePage;
    }

    @Override
    public EncryptionKey getFriendsPostEncryptionKey() {
        return friendsPostEncryptionKey;
    }

    @Override
    public EncryptionKey getFollowersPostEncryptionKey() {
        return followersPostEncryptionKey;
    }


}
