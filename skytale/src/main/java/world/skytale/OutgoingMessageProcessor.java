package world.skytale;

import world.skytale.database.DatabaseHandler;
import world.skytale.database.MailSender;
import world.skytale.model2.AttachmentFactory;
import world.skytale.model2.Chat;
import world.skytale.model2.ChatMessage;

public abstract class OutgoingMessageProcessor  {

    protected abstract MailSender getMailSender();
    protected abstract AttachmentFactory getAttachmentFactory();
    protected abstract DatabaseHandler getDatabaseHandler();


    public boolean sendChatMessage(ChatMessage chatMessage, Chat chat)
    {

    }


}
