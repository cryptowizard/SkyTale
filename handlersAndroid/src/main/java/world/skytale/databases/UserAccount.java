package world.skytale.databases;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model2.Account;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class UserAccount implements Account {

    private ContactDAO userContact;
    private PrivateKey privateKey;

   private UserAccount(KeyPair keyPair, String firstName, String lastName, String email, String picturePath)
    {
        this.privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        ID id = ID.PublicKeyID.makeID(publicKey);
        this.userContact = new ContactDAO(id,publicKey,email,firstName,lastName, Contact.TYPE_ME,picturePath);
    }


    public static Account makeNewAccount(String firstName, String lastName, String email, String picturePath)
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
        Account account = new UserAccount(keyPair,firstName,lastName,email,picturePath);
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
}
