package world.xfreemedia;

import java.security.KeyPair;
import java.security.PrivateKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.model.Account;
import world.skytale.model.Contact;

public class UserAccount extends Account  {

    private Contact userContact;
    private PrivateKey privateKey;

   private UserAccount(KeyPair keyPair, String firstName, String lastName, String email, String picturePath)
    {
        this.privateKey = keyPair.getPrivate();
        this.userContact = new Contact(keyPair.getPublic(),firstName,lastName,email);
        this.userContact.picturePath = picturePath;
        this.userContact.type = Contact.TYPE_ME;
    }


    public static Account makeNewAccount(String firstName, String lastName, String email, String picturePath)
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
        Account account = new UserAccount(keyPair,firstName,lastName,email,picturePath);
        return account;
    }




    @Override
    public Contact getUserContact() {
        return this.userContact;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
