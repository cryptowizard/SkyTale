package world.skytale.databases.databes.Tables;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import javax.crypto.SecretKey;

import world.skytale.converters.SecretKeyConventer;
import world.skytale.cyphers.AES;
import world.skytale.database.EncryptionKeyHandler;
import world.skytale.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.Tables.TableEncryptionKeys;
import world.skytale.databases.daos.EncryptionKeyDAO;
import world.skytale.model.AvaiableMessages.EncryptionKey;
import world.skytale.model.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EncryptionKeyHandlerTest {


    private final EncryptionKeyHandler encryptionKeyHandler;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public EncryptionKeyHandlerTest() {
        Context context  =  InstrumentationRegistry.getTargetContext();
        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(context);
        encryptionKeyHandler = new TableEncryptionKeys(sqlDatabaseHelper);
    }

    public static EncryptionKey makeRandomEncryptionKey()
    {
        long time = new Date().getTime();
        ID senderID = ID.generateRandomID();
        SecretKey secretKey = AES.generateNewKey();

        return new EncryptionKeyDAO(senderID,time,secretKey);
    }


    @Test
    public void addToDatabase()
    {
        EncryptionKey encryptionKey1 = makeRandomEncryptionKey();
       boolean result =  encryptionKeyHandler.addEncryptionKey(encryptionKey1);
       assertTrue(result);


       boolean resultFalse = encryptionKeyHandler.addEncryptionKey(encryptionKey1);;
       assertFalse(resultFalse);
    }


    @Test
    public void getFromDatabase() throws ItemNotFoundException {
        EncryptionKey encryptionKey1 = makeRandomEncryptionKey();
        encryptionKeyHandler.addEncryptionKey(encryptionKey1);
        EncryptionKey encryptionKey2 = encryptionKeyHandler.getEncryptionKey(encryptionKey1.getSenderID(),encryptionKey1.getTime());
        checkIfEqual(encryptionKey1, encryptionKey2);
    }

    public void checkIfEqual(EncryptionKey encryptionKey1, EncryptionKey encryptionKey2)
    {
        assertEquals(encryptionKey1.getTime(), encryptionKey2.getTime());
        assertEquals(encryptionKey1.getSenderID(), encryptionKey2.getSenderID());
        assertEquals(SecretKeyConventer.toString(encryptionKey1.getKey()),SecretKeyConventer.toString(encryptionKey2.getKey()));
    }
}
