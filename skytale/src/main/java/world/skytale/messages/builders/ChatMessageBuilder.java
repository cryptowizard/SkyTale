package world.skytale.messages.builders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Random;

import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.AES;
import world.skytale.database.DatabaseHandler;
import world.skytale.message.Messages;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Chat;
import world.skytale.model.sendable.ChatMessage;
import world.skytale.model.Contact;
import world.skytale.proto.attachments.ProtoAttachment;

public class ChatMessageBuilder extends MailBuilder <ChatMessage> {
    public static final String TYPE_TAG = "CHAT_MSG";



    public ChatMessageBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        super(attachmentFactory, databaseHandler);
    }

    @Override
    protected String getTypeTag() {
        return TYPE_TAG;
    }

    @Override
    protected byte[] buildMessageBytes(ChatMessage chatMessage) {
        return new byte[0];
    }

    public DownloadedMail makeDownloadedMail(ChatMessage chatMessage, Chat chat) throws IOException, InvalidKeyException {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(),chatMessage.getTime());


        ArrayList<Messages.Attachment> attachemtns  = ProtoAttachment.toProtoList(chatMessage.getAttachments());

        Messages.ChatMessage chatMessageProto = Messages.ChatMessage.newBuilder()
                .setMessageText(chatMessage.getMessage())
                .setRandomBytes(ByteConverter.toByteString(getRandomBytes()))
                .addAllAttachments(attachemtns)
                .build();

        byte [] messageBytes = chatMessageProto.toByteArray();
        byte [] encryptedMessage = AES.encrypt(chat.getSecretKey(),messageBytes);


        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.newBuilder()
                .setEncryptedChatMessageBytes(ByteConverter.toByteString(encryptedMessage))
                .setChatID(chat.getChatID().toLong())
                .build();


        VeryfiedMessage veryfiedMessage = new VeryfiedMessage(messageHeader,encryptedChatMessage.toByteArray(), Contact.TYPE_CHAT);
        DownloadedMail downloadedMail = super.makeDownloadedMail(veryfiedMessage);
        return downloadedMail;
    }


    private static byte [] getRandomBytes()
    {
        Random random = new Random();
        int size = random.nextInt(118)+10;
        byte [] randomBytes = new byte[size];
        random.nextBytes(randomBytes);
        return randomBytes;
    }
}
