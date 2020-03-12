package world.MessageProcessorTests;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import java.io.IOException;

import world.skytale.MessageProcessingException;
import world.skytale.databases.UserAccount;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.messages.builders.MailBuilder;
import world.skytale.model.Account;
import world.skytale.model.Attachment;
import world.skytale.model.Contact;

public class ProcessorTest {

    Context context  =  InstrumentationRegistry.getTargetContext();

    Account sender = UserAccount.makeNewAccount("sender@gmail.com");
    Account reciver = UserAccount.makeNewAccount("reciver@gmail.com");

    public VeryfiedMessage makeVerifiedMessage(DownloadedMail downloadedMail, Contact sendder)
    {
        byte [] messageBytes = null;
        for(Attachment attachment : downloadedMail.getAttachments())
        {
            if(attachment.getExtension().equals(MailBuilder.MESSAGE_EXTENSION))
            {
                try {
                    messageBytes = attachment.getFileBytes();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        try {
            VeryfiedMessage veryfiedMessage = new VeryfiedMessage(MessageHeader.parseTitle(downloadedMail.getTitle()),messageBytes,sendder);
            return veryfiedMessage;
        } catch (MessageProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
