package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.Displayable;
import world.skytale.model.MessageID;

/**
 * Comment is a displayable message that is created in a reply to a Post or another comment
 */
public interface Comment extends Sendable{

    @NonNull
    MessageID getPostID();


    /**
     * @ToDo better Names for those variables
     * <p>
     * IF the comment is a reply to another
     * ReplySenderID and ReplyTime are used in oreder to identyfy this comment
     */
    MessageID getReplyID();

    Displayable getDisplayable();





}