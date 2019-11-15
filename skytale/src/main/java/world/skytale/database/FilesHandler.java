package world.skytale.database;

import java.io.IOException;

import world.skytale.model2.Attachment;


/**
 * FilesHandler object is used to store
 */
public interface FilesHandler {


    String writeTemporaryFile (byte [] fileBytes, String extension) throws IOException;
    String saveFile(byte [] fileBytes, String extension) throws IOException;

    public String saveAttachment(Attachment attachment) throws IOException;
}
