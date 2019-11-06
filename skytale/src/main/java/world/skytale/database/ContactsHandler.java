package world.skytale.database;

import world.skytale.model.Contact;


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

     Contact getContact(String contactID) throws ContactNotFoundException;


    /**
     *
     * @param contact
     * @return true if contact was successfully added and false if contact was already in database
     */
     boolean addContact(Contact contact);


    /**
     *
     * @param contact contact with updated information
     * @return true if contact was updated succesfully  / false if contact was not found
     */
     boolean updateContact(Contact contact);


    /**
     *
     * @param contactID - contact identifier
     * @param contactType - contact type
     * @return  true if contact was updated scornfully  / false if contact was not found
     */
    boolean changeContactType(String contactID, int contactType);



     class ContactNotFoundException extends Exception
     {
         public ContactNotFoundException(String contactID)
         {
             super("Contact with ID: \""+contactID+"\" was not found in Database");
         }
     }

}
