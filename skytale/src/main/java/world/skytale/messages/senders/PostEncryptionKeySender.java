package world.skytale.messages.senders;

import java.util.ArrayList;
import java.util.List;

import world.database.AccountProvider;
import world.database.ContactsHandler;
import world.database.DatabaseHandler;
import world.database.EncryptionKeyHandler;
import world.database.MailTransporter;
import world.skytale.messages.DownloadedMail;
import world.skytale.messages.builders.PostEncryptionKeyBuilder;
import world.skytale.model.Account;
import world.skytale.model.AttachmentFactory;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.EncryptionKeyImp;
import world.skytale.model.EncryptionKey;

public class PostEncryptionKeySender  {

    private final MailTransporter mailTransporter;
    private final AttachmentFactory attachmentFactory;

    private final AccountProvider accountProvider;
    private final EncryptionKeyHandler encryptionKeyHandler;
    private final ContactsHandler contactsHandler;

    private List<Contact> allContacts;

    PostEncryptionKeyBuilder followersPostEncryptionKeyBuilder;
    PostEncryptionKeyBuilder friendsPostEncryptionKeyBuilder;

    EncryptionKey friendsEncryptionKey;
    EncryptionKey followersEncryptionKey;



    public PostEncryptionKeySender(MailTransporter mailTransporter, DatabaseHandler databaseHandler, AttachmentFactory attachmentFactory) {
        this.mailTransporter = mailTransporter;
        this.attachmentFactory = attachmentFactory;

        accountProvider = databaseHandler.getAccountProvider();
        encryptionKeyHandler = databaseHandler.getEncryptionKeyHandler();
        contactsHandler = databaseHandler.getContactsHandler();
    }


    public void updatePostEncryptionKeys(EncryptionKey friendsEncryptionKey, EncryptionKey followersEncryptionKey) throws KeySharingException {
        this.followersEncryptionKey = followersEncryptionKey;
        this.friendsEncryptionKey = friendsEncryptionKey;
        send();;
    }

    public void updatePostEncryptionKeys() throws KeySharingException {
        generateNewEncryptionKeys();
        send();
    }


    private void generateNewEncryptionKeys()
    {
        ID accountID =accountProvider.getCurrentAccount().getUserContact().getID();

        this.friendsEncryptionKey = EncryptionKeyImp.generateNewKey(accountID);
        this.followersEncryptionKey = EncryptionKeyImp.generateNewKey(accountID);

    }


    /**
     * Encryption Keys updates are send differently from other methods as they require that message
     * is encrypted with diffrent key for each recipent
     * @return
     * @throws Exception
     */
    private void send() throws KeySharingException {
        ArrayList<ID> failedMessagesIDs = new ArrayList<ID>();
        ArrayList<Exception> failureCouses = new ArrayList<>();

            addToDatabase();
            initilizeBuilders();
            getContactList();

            for(Contact contact : allContacts)
            {
                try{
                   sendToContact(contact);
                }
                catch (Exception cause)
                {
                    failedMessagesIDs.add(contact.getID());
                    failureCouses.add(cause);
                }
            }

            if(failedMessagesIDs.size()>0)
            {
                throw new KeySharingException(failedMessagesIDs,failureCouses);
            }
    }

    private boolean addToDatabase() {
        boolean keysUpdated = accountProvider.updatePostEncryptionKeys(friendsEncryptionKey, followersEncryptionKey);

        if(!keysUpdated) return  false;

        Account updatedAccount = accountProvider.getCurrentAccount();

        followersEncryptionKey = updatedAccount.getFollowersPostEncryptionKey();
        friendsEncryptionKey = updatedAccount.getFriendsPostEncryptionKey();


       boolean followersKeyAdded =  encryptionKeyHandler.addEncryptionKey(followersEncryptionKey);
       boolean friendsKeyAdded =  encryptionKeyHandler.addEncryptionKey(friendsEncryptionKey);

       return (followersKeyAdded && friendsKeyAdded);
    }

    private void initilizeBuilders()
    {
        ArrayList<EncryptionKey> followersKeyList = new ArrayList<EncryptionKey>();
        ArrayList<EncryptionKey> friendsKeyList = new ArrayList<EncryptionKey>();

        followersKeyList.add(followersEncryptionKey);

        friendsKeyList.add(followersEncryptionKey);
        friendsKeyList.add(friendsEncryptionKey);

        followersPostEncryptionKeyBuilder = new PostEncryptionKeyBuilder(attachmentFactory,accountProvider.getCurrentAccount());
        friendsPostEncryptionKeyBuilder = new PostEncryptionKeyBuilder(attachmentFactory,accountProvider.getCurrentAccount());

        followersPostEncryptionKeyBuilder.setEncryptionKeys(followersKeyList);
        friendsPostEncryptionKeyBuilder.setEncryptionKeys(friendsKeyList);
    }

    private void getContactList()
    {
        allContacts = contactsHandler.getAllContacts();
    }

    private void sendToContact(Contact contact) throws Exception {
        DownloadedMail downloadedMail;
        if(contact.isFriend())
        {
            friendsPostEncryptionKeyBuilder.setReciversContact(contact);
            downloadedMail = friendsPostEncryptionKeyBuilder.makeDownloadedMail();
        }
        else if(contact.isFollower())
        {
            followersPostEncryptionKeyBuilder.setReciversContact(contact);
            downloadedMail = followersPostEncryptionKeyBuilder.makeDownloadedMail();
        }
        else {
            return;
        }
        mailTransporter.sendMail(downloadedMail,contact.getAdress());
    }

  public static class KeySharingException extends Exception{
       private  final ArrayList<ID> failedReciverIDs;
       private final ArrayList<Exception> couses;

      public KeySharingException(ArrayList<ID> failedReciverIDs, ArrayList<Exception> couses) {
          this.failedReciverIDs = failedReciverIDs;
          this.couses = couses;
      }
  }





}
