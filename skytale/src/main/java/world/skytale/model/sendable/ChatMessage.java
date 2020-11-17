package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.Displayable;
import world.skytale.model.implementations.ID;

public interface ChatMessage extends Sendable {
    @NonNull
    ID getChatID();

    Displayable getDisplayable();
}
