package world.skytale.model2;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public interface ChatMessage {
  @NonNull ID getSenderID();
    long getTime();
    String getMessage();
    ArrayList<Attachment> getAttachments();
}
