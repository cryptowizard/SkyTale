package world.skytale;

import java.util.List;

import world.database.DatabaseHandler;
import world.database.MailTransporter;
import world.skytale.messages.senders.FriendRequestResponseSender;
import world.skytale.messages.senders.FriendRequestSender;
import world.skytale.messages.senders.PostEncryptionKeySender;
import world.skytale.messages.senders.PostSender;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.EncryptionKey;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.sendable.Post;

public class OutgoingMessageProcessor   {

private final MailTransporter mailTransporter;
private final DatabaseHandler databaseHandler;
private final AttachmentFactory attachmentFactory;

    public OutgoingMessageProcessor(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        this.mailTransporter = mailTransporter;
        this.databaseHandler = databaseHandler;
        this.attachmentFactory = attachmentFactory;
    }


    public void sendFriendRequest(FriendRequest friendRequest) throws Exception {
        FriendRequestSender friendRequestSender = new FriendRequestSender(mailTransporter,databaseHandler,attachmentFactory);
        friendRequestSender.sendFriendRequest(friendRequest);
    }

    public void sendFriendRequest(String reciversEmail) throws Exception {
        FriendRequestSender friendRequestSender = new FriendRequestSender(mailTransporter,databaseHandler,attachmentFactory);
        friendRequestSender.sendFriendRequest(reciversEmail);
    }

    public void acceptFriendRequest(FriendRequest friendRequest) throws Exception {
        FriendRequestResponseSender friendRequestResponseSender = new FriendRequestResponseSender(mailTransporter, databaseHandler,attachmentFactory);
        friendRequestResponseSender.sendFriendRequestAcceptResponse(friendRequest);
    }


    public void sharePost(Post post, List<String> adresses) throws Exception {
        PostSender postSender = new PostSender(mailTransporter, databaseHandler, attachmentFactory);
        postSender.sharePost(post,adresses);
    }

    public void sherePostWithAllFriends(Post post)
    {
        PostSender postSender = new PostSender(mailTransporter, databaseHandler, attachmentFactory);
        postSender.sharePostWithAllFriends(post);

    }

    public void updatePostEncryptionKeys(EncryptionKey friendsEncryptionKey, EncryptionKey followersEncryptionKey) throws PostEncryptionKeySender.KeySharingException {
        PostEncryptionKeySender postEncryptionKeySender = new PostEncryptionKeySender(mailTransporter,databaseHandler,attachmentFactory);
        postEncryptionKeySender.updatePostEncryptionKeys(friendsEncryptionKey, followersEncryptionKey);
    }






}
