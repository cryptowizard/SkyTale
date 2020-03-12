package world.skytale.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface ProfilePage {
    @NonNull ID getConstactID();
    @NonNull String getUsername();
    @Nullable Attachment getProfilePicture();
    String getDescription();
    List<String> getProfileLinks();
}
