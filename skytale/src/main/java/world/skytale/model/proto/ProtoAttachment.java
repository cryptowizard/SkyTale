package world.skytale.model.proto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import world.skytale.converters.ByteConverter;
import world.skytale.message.Messages;
import world.skytale.model.Attachment;
import world.skytale.model.implementations.LoadedAttachment;


/**
 * Class is used to convert Messages.Attachment into Attachment object and the other way
 * as the proto generated classes are final and can not be extended with the interface
 */
public class ProtoAttachment implements Attachment {

    private final Messages.Attachment attachment;

    public ProtoAttachment(Messages.Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public byte[] getFileBytes() {
        return attachment.getFileBytes().toByteArray();
    }

    @Override
    public String getExtension() {
        return attachment.getFileExtension();
    }

    public static Messages.Attachment toProtoAttachment(Attachment attachment) {

        if(attachment==null)  attachment = new LoadedAttachment("",new byte[0]);
        Messages.Attachment proto = null;
        try {
            proto = Messages.Attachment.newBuilder()
                    .setFileBytes(ByteConverter.toByteString(attachment.getFileBytes()))
                    .setFileExtension(attachment.getExtension())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proto;
    }

    public static ArrayList< Messages.Attachment> toProtoList(List<Attachment> attachmentList)
    {

        ArrayList< Messages.Attachment> attachments = new ArrayList<Messages.Attachment>();
        if(attachmentList==null)
        {
            return attachments;
        }
        for(Attachment attachment : attachmentList)
        {
            attachments.add(toProtoAttachment(attachment));
        }
        return attachments;
    }

    public static ArrayList< Messages.Attachment> toProtoList(Attachment [] attachmentList)
    {
        ArrayList< Messages.Attachment> attachments = new ArrayList<Messages.Attachment>();
        for(Attachment attachment : attachmentList)
        {
            attachments.add(toProtoAttachment(attachment));
        }
        return attachments;
    }

    public static ArrayList<Attachment> fromProtoList(List<Messages.Attachment> attachmentList)
    {
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();
        for(Messages.Attachment attachment : attachmentList)
        {
            attachments.add(new ProtoAttachment(attachment));
        }
        return attachments;
    }



}
