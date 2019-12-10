package world.skytale.proto.attachments;

import world.skytale.model.Attachment;
import world.skytale.model.AttachmentFactory;

public class LoadedAttachment implements Attachment {

    private final String fileExtension;
    private final byte [] fileBytes;
    public LoadedAttachment(String fileName, byte [] fileBytes) {
        this.fileExtension = fileName;
        this.fileBytes = fileBytes;
    }


    @Override
    public byte[] getFileBytes() {
        return fileBytes;
    }

    @Override
    public String getExtension() {
      return fileExtension;
    }


    public static class  LoadedAttachmentFactory implements AttachmentFactory
    {
        public static LoadedAttachmentFactory getInstance()
        {
            return new LoadedAttachmentFactory();
        }
        @Override
        public LoadedAttachment makeAttachment(String extension, byte[] fileBytes) {
            return new LoadedAttachment(extension, fileBytes);
        }


    }

}
