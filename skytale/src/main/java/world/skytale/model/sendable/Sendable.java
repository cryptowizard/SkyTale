package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.implementations.MessageID;

public interface Sendable {

    @NonNull
    MessageID getMessageID();

}
