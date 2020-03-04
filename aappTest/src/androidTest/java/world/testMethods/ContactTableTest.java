package world.testMethods;

import world.database.DatabaseHandler;
import world.database.ItemNotFoundException;
import world.skytale.model.Contact;
import static org.junit.Assert.*;
public class ContactTableTest {

    public  static void testIFcontactIsInDatabase(Contact contact, DatabaseHandler databaseHandler) throws ItemNotFoundException {
        Contact databaseContact = databaseHandler.getContactsHandler().getContact(contact.getID());

        assertEquals(contact.getID(), databaseContact.getID());
        assertEquals(contact.getAdress(), databaseContact.getAdress());

        assertTrue(databaseContact.getID().equals(contact.getID()));
        assertTrue(contact.getPublicKey().equals(databaseContact.getPublicKey()));
    }


}
