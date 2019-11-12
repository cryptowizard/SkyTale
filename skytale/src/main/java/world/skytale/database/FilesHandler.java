package world.skytale.database;

import java.io.File;
import java.io.IOException;


/**
 * FilesHandler object is used to store
 */
public interface FilesHandler {
    byte [] readFileBytes(File file) throws IOException;

    String writeTemporaryFile (byte [] fileBytes, String extension);
    String getTemporaryFilePath (String extension);

}
