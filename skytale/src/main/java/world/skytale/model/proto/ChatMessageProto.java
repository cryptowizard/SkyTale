package world.skytale.model.proto;

import androidx.annotation.NonNull;

import java.util.Random;

import world.skytale.converters.ByteConverter;
import world.skytale.message.Messages;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.MessageID;
import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.Displayable;

public class ChatMessageProto implements ChatMessage {

    private final Messages.ChatMessage protoMessage;
    private final ID chatID;
    private final MessageID messageID;


    public ChatMessageProto(Messages.ChatMessage protoMessage, ID chatID, MessageID messageID) {
        this.protoMessage = protoMessage;
        this.chatID = chatID;
            this.messageID = messageID;
    }

    public ChatMessageProto(ChatMessage chatMessage)
    {
        this.messageID = chatMessage.getMessageID();
        this.chatID = chatMessage.getChatID();
        this.protoMessage= toProtoMessage(chatMessage);
    }

    private static byte [] getRandomBytes()
    {
        Random random = new Random();
        int size = random.nextInt(118)+10;
        byte [] randomBytes = new byte[size];
        random.nextBytes(randomBytes);
        return randomBytes;
    }


    public static Messages.ChatMessage toProtoMessage(ChatMessage chatMessage)
    {
        DisplayableProto displayableProto = new DisplayableProto(chatMessage.getDisplayable());

        Messages.ChatMessage chatMessageProto = Messages.ChatMessage.newBuilder()
                .setRandomBytes(ByteConverter.toByteString(getRandomBytes()))
                .setDisplayable(displayableProto.getProtoMessage())
                .build();

        return chatMessageProto;
    }
    public Messages.ChatMessage getProtoMessage()
    {
        return this.protoMessage;
    }

    @NonNull
    @Override
    public ID getChatID() {
        return chatID;
    }

    @Override
    public Displayable getDisplayable() {
        return new DisplayableProto(protoMessage.getDisplayable());
    }

    @NonNull
    @Override
    public MessageID getMessageID() {
        return messageID;
    }
}
