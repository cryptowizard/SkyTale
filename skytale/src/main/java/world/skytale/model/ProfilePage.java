package world.skytale.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import world.skytale.model.implementations.ID;

public interface ProfilePage {
    @NonNull
    ID getConstactID();
    @NonNull String getUsername();
    @Nullable Attachment getProfilePicture();
    String getDescription();
    List<String> getProfileLinks();
}
