package world.skytale.model.attachments;

import world.skytale.utils.FileUtils;

public class AttachmentImp implements Attachment {

    private final String fileName;
    private final byte [] fileBytes;
    public AttachmentImp(String fileName, byte [] fileBytes) {
        this.fileName = fileName;
        this.fileBytes = fileBytes;
    }


    @Override
    public byte[] getFileBytes() {
        return fileBytes;
    }

    @Override
    public String getExtension() {
      return FileUtils.getExtension(fileName);
    }


}
