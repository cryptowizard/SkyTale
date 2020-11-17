package world.skytale.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.crypto.SecretKey;

import world.skytale.model.implementations.ID;

public interface Chat {
    @NonNull
    ID getChatID();
    @NonNull ID [] getParticipantIDs();
    @NonNull SecretKey getSecretKey();
    @NonNull String getChatName();
    @Nullable Attachment getChatImage();
}
