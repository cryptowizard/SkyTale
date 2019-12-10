package world.skytale.messages.builders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import world.skytale.database.DatabaseHandler;
import world.skytale.message.Messages;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.MessageHeader;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.sendable.Sendable;
import world.skytale.proto.attachments.ProtoAttachment;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.sendable.Post;

public class PostMessageBuilder extends MailBuilder {
    public static final String TYPE_TAG = "POST";

    /**
     * @param attachmentFactory The provided attachment factory provides the choice of how large attachments will be passed to downloaded mail
     * @param databaseHandler
     */
    public PostMessageBuilder(AttachmentFactory attachmentFactory, DatabaseHandler databaseHandler) {
        super(attachmentFactory, databaseHandler);

    }

    @Override
    protected String getTypeTag() {
        return null;
    }

    @Override
    protected byte[] buildMessageBytes(Sendable sendable) {
        return new byte[0];
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
