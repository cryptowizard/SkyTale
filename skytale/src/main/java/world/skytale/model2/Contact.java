package world.skytale.model2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;

public interface Contact{
    /**
     *  Constant values have been used because they take less resources than enum
     *  and can be easily extended if the client application wants to use more types
     *  The relation with smaller contactType is considered closer relation to relation with larger types
     */

     int TYPE_ME=0;
    /**
     * Type Friend means two way relation so if one User identifies contact as a friend
     * it expects that the other user also identifies them as a friend
     * Friends can share post with each other
     */
    int TYPE_FRIEND =100;


     int TYPE_SHARED=300;
     int TYPE_CHAT = 400;
     int TYPE_REQUEST=500;
    /**
     * Type Followed is two way relation user that identifies Contact as a Followed expects
     * to receive posts from them and be identified by the other party as Follower
     * @See Follower
     */
     int TYPE_FOLLOWED=600;
     int TYPE_DEFAULT=1000;


     @NonNull ID getID();
     @NonNull PublicKey getPublicKey();
     @NonNull String getAdress();

     @Nullable String getFirstName();
     @Nullable String getLastName();
     @Nullable Attachment getProfilePicture();
     public int getContactType();






}
