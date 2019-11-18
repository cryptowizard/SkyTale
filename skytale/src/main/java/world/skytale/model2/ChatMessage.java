package world.skytale.model2;

import androidx.annotation.NonNull;

import world.skytale.model.attachments.FileAttachment;

public interface ChatMessage {
    @NonNull ID getSenderID();
    long getTime();
    String getMessage();
    FileAttachment[] getAttachments();
}
