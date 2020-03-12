package world.testMethods;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.Displayable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompareDisplayable {

    public static void compareDisplayables(Displayable displayable1, Displayable displayable2)
    {
        assertEquals(displayable1.getLink(), displayable2.getLink());
        assertEquals(displayable1.getText(), displayable2.getText());
        assertEquals(displayable1.getConfiguration(), displayable2.getConfiguration());
    }


    public static void compareAttachmentLists(ArrayList<Attachment> list1, ArrayList<Attachment> list2) {
        boolean list1isEmpty = list1 == null || list1.size() == 0;
        boolean list2isEmpty = list2 == null || list2.size() == 0;

        assertEquals(list1isEmpty, list2isEmpty);


        boolean sameExtensions = bothListsContainSameExtensions(list1, list2);

        assertTrue(sameExtensions);

    }

    public static boolean bothListsContainSameExtensions(ArrayList<Attachment> list1, ArrayList<Attachment> list2)
    {
        ArrayList<Attachment> list3 = (ArrayList<Attachment>) list2.clone();

        for(Attachment attachment : list1)
        {
            for(int i=0;i<list3.size();i++)
            {
                if(list3.get(i).getExtension().equals(attachment.getExtension()))
                {
                    break;
                }
            }
            return false;
        }
        return true;
    }


}
