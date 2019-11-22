package world.skytale.messages.senders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model2.Chat;
import world.skytale.model2.ChatMessage;

public class ChatMessageSender extends MessageSender{


    private final ChatMessage chatMessage;
    private final Chat chat;

    ChatMessageBuilder chatMessageBuilder;


    public ChatMessageSender(ChatMessage chatMessage, Chat chat)
    {

        this.chatMessage = chatMessage;
        this.chat = chat;
    }

    @Override
    protected boolean addToDatabase() {
        return this.databaseHandler.getChatMessageHandler().addChatMessage(this.chatMessage, this.chat.getChatID());
    }

    @Override
    protected boolean removeFromDatabase() {
        return false;
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        final DownloadedMail downloadedMail = chatMessageBuilder.makeDownloadedMail(chatMessage, chat);
        return downloadedMail;
    }

    @Override
    protected String getRecipients() {
        return null;
    }
}
