package world.skytale.model.AvaiableMessages;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.ID;

public interface ChatMessage extends Sendable {
    @NonNull
    ID getChatID();
    @NonNull ID getSenderID();
    @NonNull  long getTime();
    String getMessage();
    ArrayList <Attachment> getAttachments();
}
