package world.skytale.messages.builders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import world.skytale.message.Messages;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.attachments.ProtoAttachment;
import world.skytale.model2.Account;
import world.skytale.model2.AttachmentFactory;
import world.skytale.model2.Post;

public class PostMessageBuilder extends MailBuilder {
    public static final String TYPE_TAG = "POST";

    public PostMessageBuilder(AttachmentFactory attachmentFactory, Account account) {
        super(attachmentFactory, account);
    }


    public DownloadedMail makeDownloadedMail(Post post) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        MessageHeader messageHeader = new MessageHeader(TYPE_TAG, getAccount().getUserContact().getID(),post.getTime());

        ArrayList<Messages.Attachment> attachemtns  = ProtoAttachment.toProtoList(post.getAttachments());


        Messages.Post postProto = Messages.Post.newBuilder()
                .setPostText(post.getText())
                .setLink(post.getLink())
                .addAllAttachments(attachemtns)
                .build();

        VeryfiedMessage veryfiedMessage = new VeryfiedMessage(messageHeader,postProto.toByteArray());
        DownloadedMail downloadedMail = super.makeDownloadedMail(veryfiedMessage);
        return downloadedMail;
    }
}
