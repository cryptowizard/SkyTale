package world.skytale.model;

import java.security.PublicKey;


public class Contact {


    public static final int TYPE_DEFAULT=10;

    public static final int TYPE_ME=0;

    public static final int TYPE_CLOSE_FRIEND=1;
    public static final int TYPE_FRIEND =2;
    public static final int TYPE_OBSERVED=4;
    public static final int TYPE_SHARED=7;
    public static final int TYPE_REQUEST=6;
    public static final int TYPE_CHAT = 5;

    
    public ID contactID;
    public PublicKey publicKey;
    public String firstName;
    public String lastName;
    public String address;
    public int type;
    public String picturePath;

    public Contact(PublicKey publicKey, String firstName, String lastName, String address) {
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactID = ID.PublicKeyID.makeID(publicKey);
    }

    public Contact(ID contactID, PublicKey publicKey, String firstName, String lastName, String address, String picturePath, int type) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.type = type;
    }


    public Contact() {
    }
}
