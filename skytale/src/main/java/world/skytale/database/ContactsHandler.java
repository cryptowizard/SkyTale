package world.skytale.database;

import world.skytale.model2.Contact;
import world.skytale.model2.ID;


/**
 * ContactsHandler interface represents object used to store and retrieve Contacts from database
 */
public interface ContactsHandler {

    /**
     *
     * @param contactID
     * @return contact with given ID
     * @throws ContactNotFoundException
     */

     Contact getContact(ID contactID) throws ContactNotFoundException, ItemNotFoundException;


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


    /**
     *
     * @param contactID - contact identifier
     * @param contactType - contact contactType
     * @return  true if contact was updated scornfully  / false if contact was not found
     */
    boolean changeContactType(ID contactID, int contactType);



     class ContactNotFoundException extends Exception
     {
         public ContactNotFoundException(ID contactID)
         {
             super("Contact with ID: \""+contactID+"\" was not found in Database");
         }
     }

}
