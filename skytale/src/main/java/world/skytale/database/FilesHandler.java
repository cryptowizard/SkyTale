package world.skytale.database;

import java.io.IOException;


/**
 * FilesHandler object is used to store
 */
public interface FilesHandler {
    byte [] readFileBytes(String filePath) throws IOException;

    String writeTemporaryFile (byte [] fileBytes, String extension) throws IOException;
    String saveFile(byte [] fileBytes, String extension) throws IOException;
}
