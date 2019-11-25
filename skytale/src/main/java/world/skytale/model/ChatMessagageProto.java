package world.skytale.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.message.Messages;
import world.skytale.model2.Attachment;
import world.skytale.model2.ChatMessage;
import world.skytale.model2.ID;

public class ChatMessagageProto implements ChatMessage {

    private final Messages.ChatMessage protoMessage;


    public ChatMessagageProto(Messages.ChatMessage protoMessage) {
        this.protoMessage = protoMessage;
    }

    @NonNull
    @Override
    public ID getSenderID() {
        return null;
    }

    @NonNull
    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public ArrayList<Attachment> getAttachments() {
        return null;
    }
}
