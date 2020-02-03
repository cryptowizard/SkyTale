package world.testMethods;

import world.skytale.model.Attachment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AttachmentTest {


    public void checkAttachments(Attachment attachment1, Attachment attachment2)
    {
        veryfyBothAttachmentsExistOrAreNull(attachment1,attachment2);
        veryfyBothAttachemtExtensions(attachment1,attachment2);
    }
    public void veryfyBothAttachmentsExistOrAreNull(Attachment attachment1, Attachment attachment2)
    {
        boolean attachment1isNull = attachment1==null;
        boolean attachment2isNull = attachment2==null;

        boolean xor = attachment1isNull ^ attachment2isNull;
        assertTrue(xor);
    }

    public void veryfyBothAttachemtExtensions(Attachment attachment1, Attachment attachment2) throws NullPointerException
    {
        if(!(attachment1==null&& attachment2==null))
           assertEquals(attachment1.getExtension(), attachment2.getExtension());
    }
}
