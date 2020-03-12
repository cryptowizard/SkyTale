package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;

import world.database.ChatHandler;
import world.database.ChatMessageHandler;
import world.database.ItemNotFoundException;
import world.skytale.cyphers.AES;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Chat;
import world.skytale.model.ID;
import world.skytale.model.proto.ChatMessageProto;


public class ChatMessageProcessor implements MessageProcessor {


    private final  ChatHandler chatHandler;
    private final ChatMessageHandler chatMessageHandler;

    public ChatMessageProcessor(ChatHandler chatHandler, ChatMessageHandler chatMessageHandler) {
        this.chatHandler = chatHandler;
        this.chatMessageHandler = chatMessageHandler;
    }


    @Override
    public void processIncoming(VeryfiedMessage veryfiedMessage) throws IOException, ItemNotFoundException, InvalidKeyException  {
        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.parseFrom(veryfiedMessage.getMessageBytes());

        Chat chat = chatHandler.getChat(new ID(encryptedChatMessage.getChatID()));

        byte [] decryptedMessageBytes = AES.decrypt(chat.getSecretKey(), encryptedChatMessage.getEncryptedChatMessageBytes().toByteArray(),veryfiedMessage.getMessageHeader().getMessageID());

        Messages.ChatMessage chatMessage = Messages.ChatMessage.parseFrom(decryptedMessageBytes);

        ChatMessageProto message = new ChatMessageProto(chatMessage,chat.getChatID(), veryfiedMessage.getMessageHeader().getMessageID());
        chatMessageHandler.addChatMessage(message );
    }





}
