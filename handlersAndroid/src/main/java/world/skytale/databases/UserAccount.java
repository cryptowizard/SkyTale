package world.skytale.databases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import world.skytale.cyphers.AccountKey;
import world.skytale.databases.model.ContactDAO;
import world.skytale.model.Account;
import world.skytale.model.Attachment;
import world.skytale.model.implementations.ID;
import world.skytale.model.ProfilePage;
import world.skytale.model.implementations.EncryptionKeyImp;
import world.skytale.model.EncryptionKey;

public class UserAccount implements Account {

    private ContactDAO userContact;
    private PrivateKey privateKey;
    EncryptionKey friendsPostEncryptionKey;
    EncryptionKey followersPostEncryptionKey;

    public void setFriendsPostEncryptionKey(EncryptionKey friendsPostEncryptionKey) {
        this.friendsPostEncryptionKey = friendsPostEncryptionKey;
    }

    public void setFollowersPostEncryptionKey(EncryptionKey followersPostEncryptionKey) {
        this.followersPostEncryptionKey = followersPostEncryptionKey;
    }



   private UserAccount(KeyPair keyPair, String email)
    {
        this.privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        ID id = ID.PublicKeyID.makeID(publicKey);
        this.userContact = new ContactDAO(id,publicKey,email);
        this.followersPostEncryptionKey = EncryptionKeyImp.generateNewKey(id,1);
        this.friendsPostEncryptionKey  = EncryptionKeyImp.generateNewKey(id,2);

    }


    public static UserAccount makeNewAccount(String email)
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
       UserAccount account = new UserAccount(keyPair,email);
        return account;
    }




    @Override
    public ContactDAO getUserContact() {
        return this.userContact;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    @Override
    public ProfilePage getUserProfilePage() {
        return new ProfilePage() {
            @NonNull
            @Override
            public ID getConstactID() {
               return userContact.getID();
            }

            @NonNull
            @Override
            public String getUsername() {
                return  userContact.address;
            }

            @Nullable
            @Override
            public Attachment getProfilePicture() {
                return null;
            }

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public List<String> getProfileLinks() {
               return new ArrayList<String>();
            }
        };
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
