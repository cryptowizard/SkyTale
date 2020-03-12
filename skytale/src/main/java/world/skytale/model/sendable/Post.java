package world.skytale.model.sendable;

import world.skytale.model.Displayable;
import world.skytale.model.ID;

public interface Post  extends Sendable{



    /**
     *  If the post is shared for the first time returns sendersID
     *  if the post is reshared returns ID of the orginal sender
     * @return
     */
         ID getOrdinalSendersID();

        Displayable getDisplayable();

}
