package world.skytale.model;

import java.util.ArrayList;


/**
 * Displayable message may be Post, ChatMessage, or Comment
 */
public interface Displayable {


    /**
     * Simple message text
     * @return String of text that is going to be displayed
     */
    String getText();


    /**
     * Any file that may be attached to the message, as Picuture or Music file
     * The only limitation is the overall message size, so large files like long videos should
     * be host on publicly avaible services (like YouTube or M4Upload) and included as links
     * @return
     */
    ArrayList<Attachment> getAttachments();

    /**
     * Post can link up to one primary online resource
     * (not including links that are shared inside posts text)
     * @return
     */
    String getLink();

    /**
     * Configuration String is the part of the message that will not be displayed to the user
     * It can be used to add to message additional information describing how it should be properly
     * displayed by the client app
     * For example 24H or 48H can be used to create stories or messages that should be displayed
     * only for limited time
     * @return configurationString
     */
    String getConfiguration();
}
