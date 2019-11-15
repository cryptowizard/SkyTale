package world.skytale.model2;


import java.io.IOException;

/**
 *  Attachment Builder interface provides an abstraction for method of creating attachments
 */
public interface AttachmentFactory {
    Attachment makeAttachment(String extension, byte [] fileBytes) throws IOException;

}
