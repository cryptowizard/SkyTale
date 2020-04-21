package world.database.Tables;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.security.KeyPair;
import java.util.List;

import world.database.ContactsHandler;
import world.database.ItemNotFoundException;
import world.skytale.cyphers.AccountKey;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.ContactDAO;
import world.skytale.model.Contact;
import world.skytale.model.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        ContactDAO contactDAO = new ContactDAO(id,keyPair.getPublic(),"email.com@com");
        return contactDAO;
    }

    @Test
    public void addData() {

        Contact contact = makeNewContact();
       boolean result1 =  contactsHandler.addContact(contact);

       assertTrue(result1);
    }

    @Test
    public void getContact() throws ItemNotFoundException {

        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);
        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
    }

    @Test
    public void setContactType() throws ItemNotFoundException {
        Contact contact = makeNewContact();
        contactsHandler.addContact(contact);


        contactsHandler.setContactIsFriend(contact.getID(), true);

        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
        assertTrue(tmp.isFriend());
        assertFalse(tmp.isFollower());
        assertFalse(tmp.isObserved());

    }

    @Test
    public void changeEmail() throws ItemNotFoundException {

        ContactDAO contact = makeNewContact();
        contactsHandler.addContact(contact);

        String mail = "Hello@newMail.com";


        contactsHandler.changeContactEmail(contact.contactID,mail);
        Contact tmp = contactsHandler.getContact(contact.getID());

        assertEquals(tmp.getID(),contact.getID());
        assertEquals(mail,tmp.getAdress());


    }

    @Test
    public void getAllContatsReturnsAllContacts()
    {

        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(context);
        sqlDatabaseHelper.clearAll(sqlDatabaseHelper.getWritableDatabase());

        contactsHandler = sqlDatabaseHelper.getTableContacts();

        for(int i =0;i<10;i++)
        {
            ContactDAO contact = makeNewContact();
            contactsHandler.addContact(contact);
        }

        List<Contact> all = contactsHandler.getAllContacts();
        assertEquals(10,all.size());
    }





}