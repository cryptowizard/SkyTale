package world.skytale.mocked;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import world.skytale.model.Attachment;
import world.skytale.model.Displayable;
import world.skytale.model.implementations.DisplayableImp;

public class MockedDataGenerator {

    public static Displayable makeDisplayable()
    {
        DisplayableImp displayableImp = new DisplayableImp();
        displayableImp.setLink(makeRandomLInk());
        displayableImp.setText(makeRandomText());
        displayableImp.setConfiguration(makeRandomText());
        displayableImp.setAttachments(randomAttachmentLIst());
        return displayableImp;
    }

    private static String makeRandomLInk()
    {
        String link = "https://www.youtube.com/watch?v=jojFdN-oysU"+new Random().nextInt(777);
        return link;
    }

    private static String makeRandomText()
    {
        byte [] randomBytes = makeRandomBytes(new Random().nextInt(777));
        return new String(randomBytes);
    }

    public static Attachment makeRandomAttachment()
    {
        final String extension = makeRandomText();
        final byte [] bytes = makeRandomBytes(new Random().nextInt(12121));

        Attachment attachment = new Attachment() {
            @Override
            public byte[] getFileBytes() throws IOException {
                return bytes;
            }

            @Override
            public String getExtension() {
                return extension;
            }
        };

        return attachment;
    }

    public static ArrayList<Attachment> randomAttachmentLIst()
    {
        int size = new Random().nextInt(7);
        ArrayList <Attachment> attachments = new ArrayList<Attachment>();
        for(int i=0;i<size;i++)
        {
            attachments.add(makeRandomAttachment());
        }
        return randomAttachmentLIst();
    }

    public static byte [] makeRandomBytes(int size)
    {
        byte [] randomBytes = new byte[size];
        new Random().nextBytes(randomBytes);
        return randomBytes;
    }





}
