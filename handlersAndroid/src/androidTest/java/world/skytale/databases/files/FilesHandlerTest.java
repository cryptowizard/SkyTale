package world.skytale.databases.files;

import android.Manifest;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import world.skytale.databases.daos.FileAttachment;
import world.skytale.model.Attachment;
import world.skytale.model.implementations.LoadedAttachment;

import static org.junit.Assert.*;

public class FilesHandlerTest {



    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    Context context  =  InstrumentationRegistry.getTargetContext();
    FilesHandler filesHandler =FilesHandler.getInstance(context);

    public FilesHandlerTest() throws FilesHandler.StoragePermissionDeniedException {
    }


    @Rule
    public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    @Test
    public void saveAttachment() throws IOException {

        byte [] randomBytes = "Hello this is what i always wanted".getBytes();
        Attachment attachment = new LoadedAttachment("zzz",randomBytes);

     String path =   filesHandler.saveAttachment(attachment);

     assertEquals("zzz",FilesHandler.getExtension(path));


     Attachment recovered = FileAttachment.fromPath(path);

     assertEquals(recovered.getExtension(),attachment.getExtension());
     //assertEquals(recovered.getFileBytes(),attachment.getFileBytes());
     assertTrue(Arrays.equals(recovered.getFileBytes(),attachment.getFileBytes()));
    }

    @Test
    public void getfileName() {
        String name = "filename";
        String path = "sdCard/bum/xff/"+name+".ext";

        String name1 = FilesHandler.getfileName(path);
        assertEquals(name,name1);
    }

    @Test
    public void getExtension() {
        String name = "filename";
        String path = "sdCard/bum/xff/"+name+".ext";

        String extension1 = FilesHandler.getExtension(path);
        assertEquals("ext",extension1);
    }


}