package world.skytale.model;

import java.io.Serializable;
import java.security.PublicKey;


public class Contact implements Serializable {
    private static final long serialVersionUID = 1321102L;


    /**
     *  Constant values have been used because they take less resources than enum
     *  and can be easily extended if the client application wants to use more types
     *  The relation with smaller type is considered closer relation to relation with larger types
     */

    public static final int TYPE_ME=0;
    /**
     * Type Friend means two way relation so if one User identifies contact as a friend
     * it expects that the other user also identifies them as a friend
     * Friends can share post with each other
     */
    public static final int TYPE_FRIEND =100;

    /**
     * Type Followed is two way relation user that identifies Contact as a Followed expects
     * to receive posts from them and be identified by the other party as Follower
     * @See Follower
     */
    public static final int TYPE_FOLLOWED=200;
    public static final int TYPE_SHARED=300;


    public static final int TYPE_CHAT = 400;
    public static final int TYPE_REQUEST=500;
    public static final int TYPE_DEFAULT=1000;



    
    public ID contactID;
    public PublicKey publicKey;
    public String address;
    private int type;

    public String firstName;
    public String lastName;
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


    public Contact(PublicKey publicKey) {
        this.publicKey = publicKey;
        this.contactID = ID.PublicKeyID.makeID(publicKey);
    }
}
