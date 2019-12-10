package world.skytale;

import world.skytale.database.DatabaseHandler;
import world.skytale.database.MailTransporter;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Chat;
import world.skytale.model.sendable.ChatMessage;

public abstract class OutgoingMessageProcessor  {

    protected abstract MailTransporter getMailSender();
    protected abstract AttachmentFactory getAttachmentFactory();
    protected abstract DatabaseHandler getDatabaseHandler();


    public boolean sendChatMessage(ChatMessage chatMessage, Chat chat)
    {
        return true;
    }


}
