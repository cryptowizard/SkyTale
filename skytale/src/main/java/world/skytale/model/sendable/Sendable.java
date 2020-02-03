package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.MessageID;

public interface Sendable {

    @NonNull
    MessageID getMessageID();

}
