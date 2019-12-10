package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.ID;

public interface ChatMessage extends Sendable {
    @NonNull
    ID getChatID();
    String getMessage();
    ArrayList <Attachment> getAttachments();
}
