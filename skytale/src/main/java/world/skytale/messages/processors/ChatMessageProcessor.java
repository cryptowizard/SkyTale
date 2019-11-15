package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import world.skytale.VeryfiedMessage;
import world.skytale.cyphers.AES;
import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.ChatMessageImp;
import world.skytale.model.attachments.ProtoAttachment;
import world.skytale.model2.Attachment;
import world.skytale.model2.Chat;
import world.skytale.model2.ChatMessage;
import world.skytale.model2.ID;


public class ChatMessageProcessor implements MessageProcessor {

    public static final String TYPE_TAG = "CHAT_MSG";
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

        ChatMessage message = buildChatMessage(chatMessage,veryfiedMessage.getMessageHeader());

        chatMessageHandler.addChatMessage(message , chat.getChatID());
    }

    private ChatMessageImp buildChatMessage(Messages.ChatMessage chatMessage, MessageHeader messageHeader) throws IOException {
        ArrayList<Attachment> attachments  =ProtoAttachment.fromProtoList(chatMessage.getAttachmentsList());

        ChatMessageImp message = new ChatMessageImp(messageHeader.getSenderID(), messageHeader.getTime(), chatMessage.getMessageText(),attachments);
        return message;
    }



}
