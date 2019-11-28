package world.skytale.databases.daos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import world.skytale.databases.files.FileAttachment;
import world.skytale.model.ID;
import world.skytale.model.ProfilePage;

public class ProfilePageDAO implements ProfilePage {


    private final long contactID;
    private final String username;
    private String profilePicturePath;
    private String description;
    private List<String> profileLinks;

    public ProfilePageDAO(long contactID, String username, String profilePicturePath, String description, List<String> profileLinks) {
        this.contactID = contactID;
        this.username = username;
        this.profilePicturePath = profilePicturePath;
        this.description = description;
        this.profileLinks = profileLinks;
    }

    @NonNull
    @Override
    public ID getConstactID() {
        return new ID(this.contactID);
    }

    @NonNull
    @Override
    public String getUsername() {
        return this.username;
    }

    @Nullable
    @Override
    public FileAttachment getProfilePicture() {
        return new FileAttachment(this.profilePicturePath);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<String> getProfileLinks() {
        return this.profileLinks;
    }

    public String getImagePath()
    {
        return profilePicturePath;
    }

}
