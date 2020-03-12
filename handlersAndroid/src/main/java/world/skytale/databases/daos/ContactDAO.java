package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import java.security.PublicKey;

import world.skytale.model.Contact;
import world.skytale.model.ID;

public class ContactDAO implements Contact {


    public final ID contactID;
    public final PublicKey publicKey;
    public final String address;

    private boolean isFriend = false;
    private boolean isFollower = false;
    private boolean isObserved = false;



    public ContactDAO(ID contactID, PublicKey publicKey, String address) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.address = address;
    }


    public ContactDAO(ID contactID, PublicKey publicKey, String address, boolean isFriend, boolean isFollower, boolean isObserved) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.address = address;
        this.isFriend = isFriend;
        this.isFollower = isFollower;
        this.isObserved = isObserved;
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
         return address;
     }

    @Override
    public boolean isFriend() {
        return isFriend;
    }

    @Override
    public boolean isFollower() {
        return isFollower;
    }

    @Override
    public boolean isObserved() {
        return isObserved;
    }


    public void setFriend(int friend) {
        isFriend = fromInt(friend);
    }

    public void setFollower(int follower) {
        isFollower = fromInt(follower);
    }

    public void setObserved(int observed) {
        isObserved = fromInt(observed);
    }

    private boolean fromInt(int number)
    {
        return (number>0);
    }
}
