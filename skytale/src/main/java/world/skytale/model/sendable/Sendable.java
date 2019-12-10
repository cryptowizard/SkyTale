package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.ID;

public interface Sendable {

    @NonNull
    ID getSenderID();
    @NonNull  long getTime();
}
