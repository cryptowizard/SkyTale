package world.MessageProcessorTests;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.database.Tables.TableEncryptionKeys;
import world.skytale.MessageProcessingException;
import world.skytale.cyphers.AES;
import world.skytale.databases.SQLDatabaseHelper;
import world.skytale.databases.daos.EncryptionKeyDAO;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.PostEncryptionKeyBuilder;
import world.skytale.messages.processors.PostEncryptionKeyProcessor;
import world.skytale.model.implementations.LoadedAttachment;
import world.skytale.model.sendable.EncryptionKey;

import static org.junit.Assert.assertEquals;

public class PostEncryptionKeyProcessorTest extends ProcessorTest {


    EncryptionKeyHandler encryptionKeyHandler = new TableEncryptionKeys(new SQLDatabaseHelper(context,"PostEncryptionKeys"));


    @Test
    public void PostEncryptionKeyProcessorTest() throws Exception
    {
        ArrayList<EncryptionKey> list = generateEncryptionKeys();

        DownloadedMail downloadedMail = makeMail(list);

        processMail(downloadedMail);

        checkAllKeysAreInReciversDatabase(list);
    }


    private ArrayList<EncryptionKey> generateEncryptionKeys()
    {
        int number = new Random().nextInt(7);
        ArrayList<EncryptionKey> list = new ArrayList<EncryptionKey>();

        for(int i=0;i<=number;i++)
        {
            SecretKey secretKey = AES.generateNewKey();
            EncryptionKey encryptionKey = new EncryptionKeyDAO(sender.getUserContact().getID(),new Date().getTime()+i,secretKey);
            list.add(encryptionKey);
        }

        return list;
    }

    private DownloadedMail makeMail(ArrayList<EncryptionKey> list) throws Exception {
        PostEncryptionKeyBuilder builder = new PostEncryptionKeyBuilder(LoadedAttachment.LoadedAttachmentFactory.getInstance(),sender);
        builder.setMessage(list,reciver.getUserContact());
        DownloadedMail downloadedMail = builder.makeDownloadedMail();
        return downloadedMail;
    }

    private void processMail(DownloadedMail downloadedMail) throws BadPaddingException, MessageProcessingException, IllegalBlockSizeException, ItemNotFoundException, InvalidKeyException, IOException {
        PostEncryptionKeyProcessor processor = new PostEncryptionKeyProcessor(reciver, encryptionKeyHandler);
        processor.processIncoming(makeVerifiedMessage(downloadedMail,sender.getUserContact()));
    }

    private void checkAllKeysAreInReciversDatabase(ArrayList<EncryptionKey> list) throws ItemNotFoundException {
        for(EncryptionKey encryptionKey : list)
        {
            EncryptionKey fromHandler = encryptionKeyHandler.getEncryptionKey(encryptionKey.getMessageID().getSenderID(),encryptionKey.getMessageID().getTime());
            assertEquals(fromHandler,encryptionKey);
        }
    }




}
