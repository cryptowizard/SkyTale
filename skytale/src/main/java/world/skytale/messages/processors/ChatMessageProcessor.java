package world.skytale.messages.processors;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Random;

import world.skytale.IncomingMessageProcessor;
import world.skytale.VeryfiedMessage;
import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.AES;
import world.skytale.database.ChatHandler;
import world.skytale.database.ChatMessageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;

import world.skytale.model.Chat;
import world.skytale.model.ChatMessage;
import world.skytale.model.ID;



public class ChatMessageProcessor implements MessageProcessor {

    public static final String TYPE_TAG = "CHAT_MSG";
  private final  ChatHandler chatHandler;
  private final ChatMessageHandler chatMessageHandler;
    public ChatMessageProcessor(IncomingMessageProcessor incomingMessageProcessor)
    {

      chatHandler = incomingMessageProcessor.getDatabaseHandler().getChatHandler();
      chatMessageHandler = incomingMessageProcessor.getDatabaseHandler().getChatMessageHandler();
    }

    @Override
    public void processIncoming(VeryfiedMessage veryfiedMessage) throws IOException, ChatHandler.ChatNotFoundException, InvalidKeyException {
        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.parseFrom(veryfiedMessage.getMessageBytes());

        Chat chat = chatHandler.getChat(new ID(encryptedChatMessage.getChatID()));

        byte [] decryptedMessageBytes = AES.decrypt(chat.secretKey, encryptedChatMessage.getEncryptedChatMessageBytes().toByteArray());

        Messages.ChatMessage chatMessage = Messages.ChatMessage.parseFrom(decryptedMessageBytes);

        ChatMessage message = buildChatMessage(chatMessage,veryfiedMessage.getMessageHeader());

        chatMessageHandler.addChatMessage(message , chat.chatID);
    }

    private ChatMessage buildChatMessage(Messages.ChatMessage chatMessage, MessageHeader messageHeader) throws IOException {
        String [] attachments  = saveAttachments(chatMessage.getAttachmentsList(),fileHandler);

        ChatMessage message = new ChatMessage();
        message.attachments = attachments;
        message.message = chatMessage.getMessageText();
        message.time = messageHeader.getTime();
        message.senderID = messageHeader.getSenderID();
        return message;
    }



    public DownloadedMail makeDownloadedMail(ChatMessage chatMessage, Chat chat) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, userAccount.getUserContact().contactID,chatMessage.time);


       ArrayList<Messages.Attachment> attachemtns  = makeAttachments(chatMessage.attachments, fileHandler);

        Messages.ChatMessage chatMessageProto = Messages.ChatMessage.newBuilder()
                .setMessageText(chatMessage.message)
                .setRandomBytes(ByteConverter.toByteString(getRandomBytes()))
                .addAllAttachments(attachemtns)
                .build();

        byte [] messageBytes = chatMessageProto.toByteArray();
        byte [] encryptedMessage = AES.encrypt(chat.secretKey,messageBytes);


        Messages.EncryptedChatMessage encryptedChatMessage = Messages.EncryptedChatMessage.newBuilder()
                .setEncryptedChatMessageBytes(ByteConverter.toByteString(encryptedMessage))
                .setChatID(chat.chatID.toLong())
                .build();


        DownloadedMail downloadedMail = super.makeDownloadedMail(messageHeader,encryptedChatMessage.toByteArray());
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
