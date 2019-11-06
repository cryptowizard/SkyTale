package world.skytale.database;

import java.io.File;
import java.io.IOException;

public interface FilesStorage {
    byte [] readFileBytes(File file) throws IOException;
}
