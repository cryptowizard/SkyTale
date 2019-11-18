package world.skytale.model;

import java.security.PublicKey;

import world.skytale.model2.Attachment;
import world.skytale.model2.ID;


public class ContactImp implements world.skytale.model2.Contact {




    public ID contactID;
    public PublicKey publicKey;
    public String address;
    public int contactType;
    public String firstName;
    public String lastName;
    public Attachment profilePicture;


    public ContactImp(PublicKey publicKey) {
        this.publicKey = publicKey;
        this.contactID = ID.PublicKeyID.makeID(publicKey);
        this.contactType = TYPE_DEFAULT;

    }

    public ContactImp(PublicKey publicKey, String firstName, String lastName, String address) {
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactID = ID.PublicKeyID.makeID(publicKey);
    }

    public ContactImp(ID contactID, PublicKey publicKey, String firstName, String lastName, String address, String picturePath, int contactType) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactType = contactType;
    }

    public int getContactType() {
        return contactType;
    }


    @Override
    public ID getID() {
        return contactID;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAdress() {
        return this.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Attachment getProfilePicture() {
        return profilePicture;
    }





}
