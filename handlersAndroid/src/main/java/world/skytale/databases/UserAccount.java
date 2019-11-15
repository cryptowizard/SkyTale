package world.skytale.databases;

import java.security.KeyPair;
import java.security.PrivateKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.model2.Account;
import world.skytale.model.ContactImp;

public class UserAccount extends Account  {

    private ContactImp userContact;
    private PrivateKey privateKey;

   private UserAccount(KeyPair keyPair, String firstName, String lastName, String email, String picturePath)
    {
        this.privateKey = keyPair.getPrivate();
        this.userContact = new ContactImp(keyPair.getPublic(),firstName,lastName,email);
        this.userContact.picturePath = picturePath;
        this.userContact.contactType = ContactImp.TYPE_ME;
    }


    public static Account makeNewAccount(String firstName, String lastName, String email, String picturePath)
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
        Account account = new UserAccount(keyPair,firstName,lastName,email,picturePath);
        return account;
    }




    @Override
    public ContactImp getUserContact() {
        return this.userContact;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
