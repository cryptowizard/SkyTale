package world.skytale.model;

import java.security.PublicKey;

public class Contact {


    public static final int TYPE_DEFAULT=10;
    public static final int TYPE_ME=3;
    public static final int TYPE_FRIEND =2;
    public static final int TYPE_CLOSE_FRIEND=1;
    public static final int TYPE_OBSERVED=4;
    public static final int TYPE_SHARED=7;
    public static final int TYPE_REQUEST=6;
    public static final int TYPE_CHAT = 5;

    
    private PublicKeyId contactID;
    private PublicKey publicKey;
    private String firstName;
    private String lastName;
    private String address;
    private int type;
    private String picturePath;

    public Contact(PublicKey publicKey, String firstName, String lastName, String address) {
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactID = new PublicKeyId(publicKey);
    }

    public Contact(PublicKeyId contactID, PublicKey publicKey, String firstName, String lastName, String address, int type) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.type = type;
    }
    
    public PublicKeyId getContactID() {
        return contactID;
    }

    public void setContactID(PublicKeyId contactID) {
        this.contactID = contactID;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAbbess() {
        return address;
    }

    public void setAbbess(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
