package world.skytale.messages.senders;

import java.util.List;

import world.database.DatabaseHandler;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.EncryptedPostMessageBuilder;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.EncryptionKey;
import world.skytale.model.sendable.Post;

public class EncryptedPostSender extends MessageSender {

    private Post post;
    private List<String> adressList;
    private EncryptionKey encryptionKey;

    public EncryptedPostSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        super(mailTransporter, databaseHandler, attachmentFactory);
    }

    public void sharePost(Post post, List<String> adressList, EncryptionKey encryptionKey) throws Exception {
        this.post = post;
        this.adressList = adressList;
        this.encryptionKey = encryptionKey;
        this.send();
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws Exception {
        EncryptedPostMessageBuilder encryptedPostMessageBuilder = new EncryptedPostMessageBuilder(attachmentFactory, account);
        encryptedPostMessageBuilder.setPost(post);
        encryptedPostMessageBuilder.setEncryptionKey(encryptionKey);
        return encryptedPostMessageBuilder.makeDownloadedMail();
    }


    @Override
    protected boolean addToDatabase() {
        return databaseHandler.getPostHandler().addPost(post);
    }

    @Override
    protected boolean removeFromDatabase() {
        return databaseHandler.getPostHandler().removePost(post);
    }

    @Override
    protected List<String> getRecipientAdresses() {
        return adressList;
    }

}
