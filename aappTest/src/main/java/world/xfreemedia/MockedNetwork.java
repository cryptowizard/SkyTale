package world.xfreemedia;

import java.util.HashMap;

import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;

public class MockedNetwork{

    HashMap<String, MockedUser>  users;



    public boolean sendMail(DownloadedMail downloadedMail, String recipents, String mailSender) {
        String [] list = recipents.split(",");
        IncomingMail incomingMail = makeInkoming(downloadedMail,mailSender);
        for(String recipent : list)
        {
            MockedUser user = users.get(recipent);
            if(user!=null)
            {
                user.receiveMail(incomingMail);
            }
        }
        return true;
    }

    private static IncomingMail makeInkoming(DownloadedMail downloadedMail, String sender)
    {
        IncomingMail incomingMail = new IncomingMail(downloadedMail.getTitle(),downloadedMail.getAttachments(),sender);
        return incomingMail;
    }
}
