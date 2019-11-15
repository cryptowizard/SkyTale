package world.skytale.databases.databes;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import world.skytale.database.ContactsHandler;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.UserAccount;
import world.skytale.model.ContactImp;

import static org.junit.Assert.assertTrue;

public class TableContactsTest {

    Context context  =  InstrumentationRegistry.getTargetContext();
    ContactsHandler contactsHandler = new SQLDatabaseHelper(context);


    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public static ContactImp makeNewContact()
    {
        ContactImp contact = UserAccount.makeNewAccount("Maciej", "Solecki", "MaciejFW@gmail.com","Image").getUserContact();
        return contact;
    }

    @Test
    public void addData() {

        ContactImp contact = makeNewContact();
       boolean result1 =  contactsHandler.addContact(contact);

       assertTrue(result1);
    }

    @Test
    public void getContact() throws ContactsHandler.ContactNotFoundException {

        ContactImp contact = makeNewContact();
        contactsHandler.addContact(contact);
        ContactImp tmp = contactsHandler.getContact(contact.contactID);

        Assert.assertEquals(tmp.contactID,contact.contactID);
    }

    @Test
    public void setContactType() throws ContactsHandler.ContactNotFoundException {
        ContactImp contact = makeNewContact();
        contactsHandler.addContact(contact);

        int type = ContactImp.TYPE_CHAT;
        contactsHandler.changeContactType(contact.contactID,type);

        ContactImp tmp = contactsHandler.getContact(contact.contactID);

        Assert.assertEquals(tmp.contactID,contact.contactID);
        Assert.assertEquals(tmp.contactType,type);

    }

    @Test
    public void updateContact() throws ContactsHandler.ContactNotFoundException {

        ContactImp contact = makeNewContact();
        contactsHandler.addContact(contact);

        String name = "newName";
        contact.firstName = name;

        contactsHandler.updateContact(contact);
        ContactImp tmp = contactsHandler.getContact(contact.contactID);

        Assert.assertEquals(tmp.contactID,contact.contactID);
        Assert.assertEquals(name,tmp.firstName);


    }


}