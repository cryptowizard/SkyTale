package world.skytale.databases.files;

import java.io.IOException;

import world.skytale.databases.daos.FileAttachment;
import world.skytale.model.AttachmentFactory;

public class FilesStore implements AttachmentFactory {


    @Override
    public FileAttachment makeAttachment(String extension, byte[] fileBytes) throws IOException {
        return null;
    }
}
