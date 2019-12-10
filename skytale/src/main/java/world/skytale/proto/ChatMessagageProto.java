package world.skytale.proto;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.message.Messages;
import world.skytale.model.Attachment;
import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.ID;

public class ChatMessagageProto implements ChatMessage {

    private final Messages.ChatMessage protoMessage;
    private final ID chatID;


    public ChatMessagageProto(Messages.ChatMessage protoMessage, ID chatID) {
        this.protoMessage = protoMessage;
        this.chatID = chatID;
    }

    @NonNull
    @Override
    public ID getChatID() {
        return chatID;
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
