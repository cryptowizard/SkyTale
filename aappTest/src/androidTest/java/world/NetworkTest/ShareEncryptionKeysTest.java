package world.NetworkTest;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import world.database.ItemNotFoundException;
import world.database.Tables.TableEncryptionKeys;
import world.skytale.converters.ByteConverter;
import world.skytale.databases.daos.EncryptionKeyDao;
import world.skytale.messages.senders.PostEncryptionKeySender;
import world.skytale.model.implementations.EncryptionKeyImp;
import world.skytale.model.EncryptionKey;
import world.xfreemedia.FriendRelation;
import world.xfreemedia.MockedNetwork;
import world.xfreemedia.MockedUser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ShareEncryptionKeysTest{

    public final int INITIAL_USER_NUMBER = 3;
    MockedNetwork mockedNetwork;
    Context context  =  InstrumentationRegistry.getTargetContext();

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Before
    public void Before() throws Exception {
        mockedNetwork = new MockedNetwork(INITIAL_USER_NUMBER, context);
        FriendRelation.makeUsersFriends(mockedNetwork.getUser(0), mockedNetwork.getUser(1));
    }


    @Test
    public void friendsCanRecivesKeysFromEachOther() throws PostEncryptionKeySender.KeySharingException {
        MockedUser sender = mockedNetwork.getUser(0);

        EncryptionKey encryptionKey0 = EncryptionKeyImp.generateNewKey(sender.getUserID());
        EncryptionKey encryptionKey1 = EncryptionKeyImp.generateNewKey(sender.getUserID());

        Log.i("timebzz2",encryptionKey0.getKeyID().toString());
        Log.i("timebzz2",encryptionKey1.getKeyID().toString());
        sender.outgoinMessageProcessor.updatePostEncryptionKeys(encryptionKey0,encryptionKey1);


        ArrayList<EncryptionKeyDao> allKeys = ((TableEncryptionKeys)mockedNetwork.getUser(1).skyTaleDatabaseHandler.getEncryptionKeyHandler()).getAll();

        verifyUserHasEncryptionKey(mockedNetwork.getUser(1),encryptionKey0);
        //verifyUserHasEncryptionKey(mockedNetwork.getUser(1),encryptionKey1);

    }

    private static void verifyUserHasEncryptionKey(MockedUser mockedUser , EncryptionKey encryptionKey){
        try {
            EncryptionKey key = mockedUser.skyTaleDatabaseHandler.getEncryptionKeyHandler().getEncryptionKey(encryptionKey.getKeyID());
            assertEquals(ByteConverter.toString(key.getKey().getEncoded()),ByteConverter.toString(encryptionKey.getKey().getEncoded()));
        } catch (ItemNotFoundException e) {
            fail();
            e.printStackTrace();
        }
    }

}
