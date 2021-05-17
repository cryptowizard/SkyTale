package world.database.Tables;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import javax.crypto.SecretKey;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.skytale.converters.SecretKeyConventer;
import world.skytale.cyphers.AES;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.model.EncryptionKeyDAO;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EncryptionKeyHandlerTest {

    private static final String TAG = "EncryptionKeyHandlerTes";


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
        int type = 123;
        ID senderID = ID.generateRandomID();
        SecretKey secretKey = AES.generateNewKey();



        return new EncryptionKeyDAO(senderID.toLong(),type,secretKey, new Date().getTime());
    }

    public static EncryptionKey makeRandomEncryptionKey(ID senderID, int type)
    {
        SecretKey secretKey = AES.generateNewKey();

        return new EncryptionKeyDAO(senderID.toLong(),type,secretKey, new Date().getTime());
    }

    @Test
    public void addToDatabase()
    {

        Log.i(TAG, "addToDatabase: Tests running 2222 ");
        EncryptionKey encryptionKey1 = makeRandomEncryptionKey();
       boolean result =  encryptionKeyHandler.addEncryptionKey(encryptionKey1);
       assertTrue(result);

        Log.i(TAG, "addToDatabase: Tests running 22223 ");

       boolean resultTrue = encryptionKeyHandler.addEncryptionKey(encryptionKey1);;
       assertTrue(resultTrue);
    }


    @Test
    public void getFromDatabase() throws ItemNotFoundException {
        EncryptionKey encryptionKey1 = makeRandomEncryptionKey();
        encryptionKeyHandler.addEncryptionKey(encryptionKey1);
        EncryptionKey encryptionKey2 = encryptionKeyHandler.getEncryptionKey(encryptionKey1.getKeyID());
        checkIfEqual(encryptionKey1, encryptionKey2);
    }

    @Test
    public void removeFromDatabase()
    {
        EncryptionKey encryptionKey1 = makeRandomEncryptionKey();
        encryptionKeyHandler.addEncryptionKey(encryptionKey1);

        ((TableEncryptionKeys) encryptionKeyHandler).removeEncryptionKey(encryptionKey1.getKeyID());


        boolean keyFound = false;
        try {
            EncryptionKey encryptionKey2 = encryptionKeyHandler.getEncryptionKey(encryptionKey1.getKeyID());
            keyFound = encryptionKey2!=null;
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }

        assertFalse(keyFound);
    }

    @Test
    public void getKeyWithTheLowestType() throws ItemNotFoundException
    {

        ID senderID = ID.generateRandomID();

        EncryptionKey ek0 = makeRandomEncryptionKey(senderID,100);
        EncryptionKey ek1 = makeRandomEncryptionKey(senderID,1);
        EncryptionKey ek2 = makeRandomEncryptionKey(senderID, 2);


        encryptionKeyHandler.addEncryptionKey(ek1);
        encryptionKeyHandler.addEncryptionKey(ek2);

        EncryptionKey ek3 = encryptionKeyHandler.getEncryptionKeyWithTheLowestType(senderID);

        checkIfEqual(ek3, ek1);
    }

    public void checkIfEqual(EncryptionKey encryptionKey1, EncryptionKey encryptionKey2)
    {
        assertEquals(encryptionKey1.getKeyID().getKeyType(), encryptionKey2.getKeyID().getKeyType());
        assertEquals(encryptionKey1.getKeyID().getSenderID(), encryptionKey2.getKeyID().getSenderID());
        assertEquals(SecretKeyConventer.toString(encryptionKey1.getKey()),SecretKeyConventer.toString(encryptionKey2.getKey()));
    }
}
