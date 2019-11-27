package world.skytale.databases.daos;

import androidx.annotation.NonNull;

import java.security.PublicKey;

import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class ContactDAO implements Contact {


    public final ID contactID;
    public final PublicKey publicKey;
    public final String address;
    public int contactType;


    public ContactDAO(ID contactID, PublicKey publicKey, String address,int contactType) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.address = address;
        this.contactType = contactType;
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
     public int getContactType() {
         return this.contactType;
     }
 }
