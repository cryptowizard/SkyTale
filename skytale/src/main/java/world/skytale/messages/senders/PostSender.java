package world.skytale.messages.senders;

import java.util.List;

import world.database.DatabaseHandler;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.PostMessageBuilder;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.Post;

public class PostSender extends MessageSender {

    private Post post;
    private List<String> adressList;

    public PostSender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        super(mailTransporter, databaseHandler, attachmentFactory);
    }

    public void sharePost(Post post, List<String> adressList) throws Exception {
        this.post = post;
        this.adressList = adressList;
        this.send();
    }

    public void sharePostWithAllFriends(Post post)
    {

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
    protected DownloadedMail buildDownloadedMail() throws Exception {
        PostMessageBuilder postMessageBuilder = new PostMessageBuilder(attachmentFactory,account);
        postMessageBuilder.setPost(post);
        return postMessageBuilder.makeDownloadedMail();
    }

    @Override
    protected List<String> getRecipientAdresses() {
        return adressList;
    }
}
