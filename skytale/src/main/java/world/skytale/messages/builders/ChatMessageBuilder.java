package world.skytale.messages.builders;

import java.security.InvalidKeyException;

import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.AES;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Chat;
import world.skytale.model.proto.ChatMessageProto;
import world.skytale.model.sendable.ChatMessage;

public class ChatMessageBuilder extends MailBuilder  {
    public static final String TYPE_TAG = "CHAT_MSG";

    private Chat chat;
    private ChatMessage chatMessage;

    public ChatMessageBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }


    public ChatMessageBuilder setMessage(Chat chat, ChatMessage chatMessage)
    {
        this.chatMessage=chatMessage;
        this.chat=chat;
        return this;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ChatMessageBuilder setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
        return this;
    }

    @Override
    protected void checkIfMessageIsSet() throws Exception {
        if(this.chat == null) throw new Exception("Hello");
        if(this.chatMessage == null) throw new Exception("Chat Message Not set ");
    }


    @Override
    protected MessageHeader buildMessageHeader() {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, chatMessage.getMessageID());
        return messageHeader;
    }

    @Override
    protected byte[] buildMessageBytes() throws InvalidKeyException {
        byte [] messageBytes = ChatMessageProto.toProtoMessage(chatMessage).toByteArray();
        byte [] encryptedMessage = AES.encrypt(chat.getSecretKey(),messageBytes, chatMessage.getMessageID());


        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.newBuilder()
                .setEncryptedChatMessageBytes(ByteConverter.toByteString(encryptedMessage))
                .setChatID(chat.getChatID().toLong())
                .build();

        return encryptedChatMessage.toByteArray();
    }




}
