package world.skytale.model.proto;

import java.util.ArrayList;

import world.skytale.message.Messages;
import world.skytale.model.Attachment;
import world.skytale.model.Displayable;

public class DisplayableProto implements Displayable {

    private final Messages.Displayable protoMessage;

    public DisplayableProto(Messages.Displayable protoMessage) {
        this.protoMessage = protoMessage;
    }

    public DisplayableProto(Displayable displayable)
    {
        Messages.Displayable displayableProto = toProtoMessage(displayable);
        this.protoMessage = displayableProto;
    }


    public static Messages.Displayable toProtoMessage(Displayable displayable)
    {
        if(displayable==null) return Messages.Displayable.getDefaultInstance();

        ArrayList<Messages.Attachment> attachemtns  = ProtoAttachment.toProtoList(displayable.getAttachments());

        Messages.Displayable displayableProto = Messages.Displayable.newBuilder()
                .setText(displayable.getText())
                .setLink(displayable.getLink())
                .setConfiguration(displayable.getConfiguration())
                .addAllAttachments(attachemtns)
                .build();


        return displayableProto;
    }

    public Messages.Displayable getProtoMessage()
    {
        return protoMessage;
    }

    @Override
    public String getText() {
        return protoMessage.getText();
    }

    @Override
    public ArrayList<Attachment> getAttachments() {
        return ProtoAttachment.fromProtoList(protoMessage.getAttachmentsList());
    }

    @Override
    public String getLink() {
        return protoMessage.getLink();
    }

    @Override
    public String getConfiguration() {
        return protoMessage.getConfiguration();
    }
}
