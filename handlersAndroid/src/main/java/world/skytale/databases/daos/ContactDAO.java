package world.skytale.databases.daos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;

import world.skytale.model.attachments.FileAttachment;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class ContactDAO implements Contact {


    public final ID contactID;
    public final PublicKey publicKey;
    public final String address;
    public int contactType;
    public String firstName;
    public String lastName;
    public FileAttachment profilePicture;


    public ContactDAO(ID id, PublicKey publickKey, String first, String last, String email, String picture, int type) {
        this.contactID = id;
        this.publicKey = publickKey;
        this.firstName = first;
        this.lastName = last;
        this.address = email;
        this.profilePicture = FileAttachment.fromPath(picture);
        this.contactType = type;
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
         return profilePicture;
     }

     @Override
     public int getContactType() {
         return 0;
     }
 }
