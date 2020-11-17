package world.skytale.messages.senders;

import java.util.ArrayList;
import java.util.List;

import world.database.DatabaseHandler;
import world.database.ItemNotFoundException;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.ChatMessageBuilder;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Chat;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.sendable.ChatMessage;

public class ChatMessageSender extends MessageSender{


    private ChatMessage chatMessage;
    private  Chat chat;

    protected ChatMessageSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        super(mailTransporter, databaseHandler, attachmentFactory);
    }


    public void sendChatMessage(ChatMessage chatMessage, Chat chat) throws Exception {
      this.chat =  chat;
      this.chatMessage = chatMessage;
      send();
    }


    public void snedChatMessage(ChatMessage chatMessage) throws ItemNotFoundException {
        this.chat = databaseHandler.getChatHandler().getChat(chatMessage.getChatID());

    }



    @Override
    protected boolean addToDatabase() {
        return this.databaseHandler.getChatMessageHandler().addChatMessage(this.chatMessage);
    }

    @Override
    protected boolean removeFromDatabase() {
        return this.databaseHandler.getChatMessageHandler().removeChatMessage(chatMessage);
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws Exception {

       ChatMessageBuilder builder = new ChatMessageBuilder(attachmentFactory,account);
       builder.setMessage(chat,chatMessage);
        final DownloadedMail downloadedMail = builder.makeDownloadedMail();
        return downloadedMail;
    }

    @Override
    protected List<String> getRecipientAdresses() {
        ID[] participantIDs  = chat.getParticipantIDs();
        List<String> adresses = new ArrayList<String>();
        for(ID id : participantIDs)
        {
            try {
                Contact recipent = databaseHandler.getContactsHandler().getContact(id);
                if(!recipent.getID().equals(account.getUserContact().getID()))
                {
                   adresses.add(recipent.getAdress());
                }

            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }
        }

       return adresses;
    }
}
