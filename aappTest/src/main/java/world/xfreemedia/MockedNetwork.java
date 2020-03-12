package world.xfreemedia;

import android.content.Context;

import java.util.HashMap;

import world.skytale.MessageProcessingException;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.IncomingMail;

public class MockedNetwork  {

    private  final HashMap<String, MockedUser>  users = new HashMap<String, MockedUser>();
    private final Context context;


    public MockedNetwork(int initialUsersNumber, Context context)
    {
        this.context = context;
        for(int i=0;i<initialUsersNumber;i++)
        {
            addRandomUser();
        }
    }

    public int addRandomUser()
    {
        String nextMail = nextMail();
        MockedUser user = new MockedUser(getMailTransporter(nextMail),nextMail, context);
        users.put(nextMail,user);
        return users.size()-1;
    }

    private MailTransporter getMailTransporter(String email)
    {
        return new EmailSender(email,this);
    }
    private String nextMail()
    {
        return "Hello" + users.size()+"@xfreemedia.com";
    }

    public void addUser(MockedUser mockedUser)
    {
        String mail = mockedUser.getEmail();
        users.put(mail, mockedUser);
    }

    public MockedUser getUser(String mail)
    {
        return users.get(mail);

    }

    public MockedUser getUser(int index)
    {
        index = index% users.size();
        return (MockedUser) users.values().toArray()[index];
    }

    public boolean sendMail(DownloadedMail downloadedMail, String recipents, String mailSender) throws MessageProcessingException {
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





    public static class EmailSender implements MailTransporter
    {
        private final String mailSender;
        private final MockedNetwork mockedNetwork;

        public EmailSender(String mailSender, MockedNetwork mockedNetwork) {
            this.mailSender = mailSender;
            this.mockedNetwork = mockedNetwork;
        }

        @Override
        public boolean sendMail(DownloadedMail downloadedMail, String recipents) throws MessageProcessingException {

                return this.mockedNetwork.sendMail(downloadedMail, recipents, mailSender);

        }
    }
}
