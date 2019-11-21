package world.skytale.model2;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public interface Post {

        @NonNull
        ID getSendersID();
        long getTime();

    /**
     *  If the post is shared for the first time returns sendersID
     *  if the post is reshared returns ID of the orginal sender
     * @return
     */
         ID getOrginalSendersID();


         String getText();
         ArrayList<Attachment> getAttachments();

    /**
     * Post can link up to one primary online resource
     * (not including links that are shared inside posts text)
     * @return
     */
    String getLink();

}
