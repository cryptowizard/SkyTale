package world.skytale.databases.daos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;

import world.skytale.databases.files.FileAttachment;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class ContactDAO implements Contact {


    public final ID contactID;
    public final PublicKey publicKey;
    public final String address;

    public String firstName;
    public String lastName;
    public int contactType;
    @Nullable
    public String profilePicture;

    public ContactDAO(ID contactID, PublicKey publicKey, String address, String firstName, String lastName, int contactType, String profilePicture) {
        this.contactID = contactID;
        this.publicKey = publicKey;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactType = contactType;
        this.profilePicture = profilePicture;
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

     @Nullable
     @Override
     public String getFirstName() {
         return firstName;
     }

     @Nullable
     @Override
     public String getLastName() {
         return lastName;
     }

     @Nullable
     @Override
     public FileAttachment getProfilePicture() {
         return FileAttachment.fromPath(profilePicture);
     }

     @Override
     public int getContactType() {
         return this.contactType;
     }
 }
