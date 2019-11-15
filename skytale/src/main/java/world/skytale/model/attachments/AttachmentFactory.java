package world.skytale.model.attachments;


/**
 *  Attachment Builder interface provides an abstraction for method of creating attachments
 */
public interface AttachmentBuilder {
    Attachment makeAttachment(String extension, byte [] fileBytes);

}
