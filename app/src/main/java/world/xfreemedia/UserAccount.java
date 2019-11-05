package world.xfreemedia;

import java.security.PrivateKey;

import world.skytale.database.AccountProvider;
import world.skytale.model.Account;
import world.skytale.model.Contact;

public class UserAccount extends Account implements AccountProvider {

    private Contact userContact;
    private PrivateKey privateKey;

    @Override
    public Account getCurrentAccount() {
        return null;
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
