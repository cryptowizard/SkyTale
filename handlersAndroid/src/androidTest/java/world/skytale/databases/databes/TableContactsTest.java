package world.skytale.databases.databes;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.security.KeyPair;

import world.skytale.cyphers.AccountKey;
import world.skytale.database.ContactsHandler;
import world.skytale.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableContactsTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
    ContactsHandler contactsHandler = new SQLDatabaseHelper(context).getTableContacts();


    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public static ContactDAO makeNewContact()
    {
        KeyPair keyPair = AccountKey.generateKeyPair();
        ID id = ID.PublicKeyID.makeID(keyPair.getPublic());
        ContactDAO contactDAO = new ContactDAO(id,keyPair.getPublic(),"email.com@com",1);
        return contactDAO;
    }

    @Test
    public void addData() {

        Contact contact = makeNewContact();
       boolean result1 =  contactsHandler.addContact(contact);

       assertTrue(result1);
    }

    @Test
    public void getContact() throws ItemNotFoundException, ContactsHandler.ContactNotFoundException {

        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);
        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
    }

    @Test
    public void setContactType() throws ContactsHandler.ContactNotFoundException, ItemNotFoundException {
        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);

        int type = Contact.TYPE_CHAT;
        contactsHandler.changeContactType(contact.getID(),type);

        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
        assertEquals(type,tmp.getContactType());

    }

    @Test
    public void changeEmail() throws ContactsHandler.ContactNotFoundException, ItemNotFoundException {

        ContactDAO contact = makeNewContact();
        contactsHandler.addContact(contact);

        String mail = "Hello@newMail.com";


        contactsHandler.changeContactEmail(contact.contactID,mail);
        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
        assertEquals(mail,tmp.getAdress());


    }


}