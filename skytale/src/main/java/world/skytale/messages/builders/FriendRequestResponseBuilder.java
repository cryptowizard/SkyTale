package world.skytale.messages.builders;

import com.google.protobuf.ByteString;

import java.security.PublicKey;

import javax.crypto.SecretKey;

import world.skytale.converters.ByteConverter;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.cyphers.ECCipher;
import world.skytale.message.Messages;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Contact;
import world.skytale.model.ProfilePage;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.proto.ProfilePageProto;

public class FriendRequestResponseBuilder extends MailBuilder {

    public static final String TYPE_TAG = "FRIEND_RESP";


    private FriendRequest friendRequest;
    private Contact newFriendContact;
    private SecretKey friendsChatKey;


    public void setMessage(FriendRequest friendRequest, Contact newFriendContact, SecretKey friendsChatKey)
    {
        setFriendRequest(friendRequest);
        setFriendsChatKey(friendsChatKey);
        setNewFriendContact(newFriendContact);
    }
    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    public void setNewFriendContact(Contact newFriendContact) {
        this.newFriendContact = newFriendContact;
    }

    public void setFriendsChatKey(SecretKey friendsChatKey) {
        this.friendsChatKey = friendsChatKey;
    }

    public FriendRequestResponseBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }

    @Override
    protected void checkIfMessageIsSet() throws Exception {
            if(friendRequest==null) throw new Exception("Hello");
            if(newFriendContact==null) throw new Exception("");
            if(friendsChatKey==null) throw new Exception("");
    }

    @Override
    protected MessageHeader buildMessageHeader() {
        return  new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(),friendRequest.getMessageID().getTime());

    }

    @Override
    protected byte[] buildMessageBytes() throws Exception {
        ProfilePage myProfilePage = getAccount().getUserProfilePage();
        byte [] chatSecretKeyBytes = encryptSecretKey(friendsChatKey, newFriendContact.getPublicKey());

        ByteString chatSecretKey = ByteConverter.toByteString(chatSecretKeyBytes);
        Messages.ProfilePage protoProfilePage = ProfilePageProto.toProto(myProfilePage);

        Messages.FriendRequestAcceptRespone  friendRequestAcceptRespone= Messages.FriendRequestAcceptRespone.newBuilder()
           .setChatEncryptionKey(chatSecretKey)
                .setProfilePage(protoProfilePage)
                .build();

        return friendRequestAcceptRespone.toByteArray();
    }

    private byte [] encryptSecretKey(SecretKey secretKey, PublicKey friendsPublicKey)
    {
        byte [] m = SecretKeyConventer.toBytes(secretKey);
        return ECCipher.encrypt(friendsPublicKey, m);
    }







}
