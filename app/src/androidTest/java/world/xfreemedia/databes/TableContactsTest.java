package world.xfreemedia.databes;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import world.skytale.database.ContactsHandler;
import world.skytale.model.Contact;
import world.xfreemedia.UserAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableContactsTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
    ContactsHandler contactsHandler = new SQLDatabaseHelper(context);


    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public static Contact makeNewContact()
    {
        Contact   contact = UserAccount.makeNewAccount("Maciej", "Solecki", "MaciejFW@gmail.com","Image").getUserContact();
        return contact;
    }

    @Test
    public void addData() {

        Contact contact = makeNewContact();
       boolean result1 =  contactsHandler.addContact(contact);

       assertTrue(result1);
    }

    @Test
    public void getContact() throws ContactsHandler.ContactNotFoundException {

        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);
        Contact tmp = contactsHandler.getContact(contact.contactID);

        assertEquals(tmp.contactID,contact.contactID);
    }

    @Test
    public void setContactType() throws ContactsHandler.ContactNotFoundException {
        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);

        int type = Contact.TYPE_CHAT;
        contactsHandler.changeContactType(contact.contactID,type);

        Contact tmp = contactsHandler.getContact(contact.contactID);

        assertEquals(tmp.contactID,contact.contactID);
        assertEquals(tmp.type,type);

    }

    @Test
    public void updateContact() throws ContactsHandler.ContactNotFoundException {

        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);

        String name = "newName";
        contact.firstName = name;

        contactsHandler.updateContact(contact);
        Contact tmp = contactsHandler.getContact(contact.contactID);

        assertEquals(tmp.contactID,contact.contactID);
        assertEquals(name,tmp.firstName);


    }


}