package world.skytale.model.AvaiableMessages;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.ID;

public interface Post {

        @NonNull
        ID getSenderID();
        long getTime();

    /**
     *  If the post is shared for the first time returns sendersID
     *  if the post is reshared returns ID of the orginal sender
     * @return
     */
         ID getOrdinalSendersID();


         String getText();
         ArrayList<Attachment> getAttachments();

    /**
     * Post can link up to one primary online resource
     * (not including links that are shared inside posts text)
     * @return
     */
    String getLink();

}