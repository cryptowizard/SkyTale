package world.database;

import java.util.List;

import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;


/**
 * ContactsHandler interface represents object used to store and retrieve Contacts from database
 */
public interface ContactsHandler {

    /**
     *
     * @param contactID
     * @return contact with given ID
     * @throws ItemNotFoundException
     */

     Contact getContact(ID contactID) throws ItemNotFoundException;


    /**
     *
     * @param contact
     * @return true if contact was successfully added and false if contact was already in database
     */
     boolean addContact(Contact contact);


    /**
     *
     * @param contactID
     * @return true if contact was updated succesfully  / false if contact was not found
     */
     boolean changeContactEmail(ID contactID, String newAddress);




    boolean setContactIsFriend(ID contactID, boolean isFriend);
    boolean setContactIsFollower(ID contactID, boolean isFollower);
    boolean setContactIsObserved(ID contactID, boolean isObserved);

    List<Contact> getAllContacts();
    List<Contact> getAllContactsThat(boolean isFriend, boolean isFollower, boolean isObserved);


}
