package world.skytale.model.implementations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.crypto.SecretKey;

import world.skytale.model.Attachment;
import world.skytale.model.Chat;
import world.skytale.model.ID;
import world.skytale.model.ProfilePage;

public class FriendsChat implements Chat {

    private final ID userID;
    private final ProfilePage friendsProfilePage;
    private final SecretKey secretKey;




    public FriendsChat(ID userID, ProfilePage friendsProfilePage, SecretKey secretKey) {
        this.friendsProfilePage = friendsProfilePage;
        this.secretKey = secretKey;
        this.userID = userID;

    }


    @NonNull
    @Override
    public ID getChatID() {
        return generateFriendsChatID(getParticipantIDs());
    }


    public static ID generateFriendsChatID(ID [] participantIDs)
    {
        long xorIDs = participantIDs[0].toLong() ^ participantIDs[1].toLong();
        return new ID(xorIDs);
    }

    @NonNull
    @Override
    public ID[] getParticipantIDs() {
        return new ID[]{userID, friendsProfilePage.getConstactID()};
    }

    @NonNull
    @Override
    public SecretKey getSecretKey() {
        return secretKey;
    }

    @NonNull
    @Override
    public String getChatName() {
        return friendsProfilePage.getUsername();
    }

    @Nullable
    @Override
    public Attachment getChatImage() {
        return friendsProfilePage.getProfilePicture();
    }
}
