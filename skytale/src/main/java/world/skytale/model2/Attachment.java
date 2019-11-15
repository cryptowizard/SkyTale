package world.skytale.model2;


import java.io.IOException;

/**
 *  Attachment class if used to represent a file stored in anyway
 */
public interface Attachment {
    byte [] getFileBytes() throws IOException;
    String getExtension();
}
