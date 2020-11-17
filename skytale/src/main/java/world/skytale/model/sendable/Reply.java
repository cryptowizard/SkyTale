package world.skytale.model.sendable;

import androidx.annotation.NonNull;

import world.skytale.model.Displayable;
import world.skytale.model.implementations.MessageID;

/**
 * Comment is a displayable message that is created in a reply to a Post or another comment
 */
public interface Reply extends Sendable{

    /**
     *  orginalContentID can be used in order to simplyfy database querees on recivers device
     *
     * @return the id of the first Post in the reply chain
     * if the reply is to another reply orginalContentID should be the same
     * if the reply is to a post orginalContentID is the messageID of the Post
     * if the reply is to a chatMessage orginalMessageID should contain chatID and time = 0
     */
    @NonNull
    MessageID getOrginalContentID();



    /**
     * @ToDo better Names for those variables
     * <p>
     * IF the comment is a reply to another
     * ReplySenderID and ReplyTime are used in oreder to identyfy this comment
     */
    MessageID getReplyID();

    Displayable getDisplayable();





}