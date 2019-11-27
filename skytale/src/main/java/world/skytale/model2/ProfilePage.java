package world.skytale.model2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public interface ProfilePage {
    @NonNull ID getConstactID();
    @NonNull String getUsername();
    @Nullable Attachment getProfilePicture();
    String getDescription();
    ArrayList<String> getProfileLinks();
}
