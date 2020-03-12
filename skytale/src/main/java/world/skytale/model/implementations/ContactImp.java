package world.skytale.model.implementations;

import androidx.annotation.NonNull;

import java.security.PublicKey;

import world.skytale.model.Contact;
import world.skytale.model.ID;

public class ContactImp implements Contact {

    private final ID contactID;
    private final PublicKey publicKey;
    private final String adress;

    public ContactImp(ID contactID, PublicKey publicKey, String adress) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.adress = adress;
    }

    @NonNull
    @Override
    public ID getID() {
        return contactID;
    }

    @NonNull
    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @NonNull
    @Override
    public String getAdress() {
        return adress;
    }

    @Override
    public boolean isFriend() {
        return false;
    }

    @Override
    public boolean isFollower() {
        return false;
    }

    @Override
    public boolean isObserved() {
        return false;
    }
}
