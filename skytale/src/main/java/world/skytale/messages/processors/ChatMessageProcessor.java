package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import world.skytale.messages.VeryfiedMessage;
import world.skytale.cyphers.AES;
import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.proto.ChatMessageImp;
import world.skytale.proto.attachments.ProtoAttachment;
import world.skytale.model.Attachment;
import world.skytale.model.Chat;
import world.skytale.model.AvaiableMessages.ChatMessage;
import world.skytale.model.ID;


public class ChatMessageProcessor implements MessageProcessor {


    private final  ChatHandler chatHandler;
    private final ChatMessageHandler chatMessageHandler;

    public ChatMessageProcessor(ChatHandler chatHandler, ChatMessageHandler chatMessageHandler) {
        this.chatHandler = chatHandler;
        this.chatMessageHandler = chatMessageHandler;
    }


    @Override
    public void processIncoming(VeryfiedMessage veryfiedMessage) throws IOException, ChatHandler.ChatNotFoundException, InvalidKeyException {
        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.parseFrom(veryfiedMessage.getMessageBytes());

        Chat chat = chatHandler.getChat(new ID(encryptedChatMessage.getChatID()));

        byte [] decryptedMessageBytes = AES.decrypt(chat.getSecretKey(), encryptedChatMessage.getEncryptedChatMessageBytes().toByteArray());

        Messages.ChatMessage chatMessage = Messages.ChatMessage.parseFrom(decryptedMessageBytes);

        ChatMessage message = buildChatMessage(chatMessage,veryfiedMessage.getMessageHeader(), chat.getChatID());

        chatMessageHandler.addChatMessage(message );
    }

    private ChatMessage buildChatMessage(Messages.ChatMessage chatMessage, MessageHeader messageHeader, ID chatID) throws IOException {
        ArrayList<Attachment> attachments  =ProtoAttachment.fromProtoList(chatMessage.getAttachmentsList());

        ChatMessageImp message = new ChatMessageImp(chatID, messageHeader.getSenderID(), messageHeader.getTime(), chatMessage.getMessageText(),attachments);
        return message;
    }



}
