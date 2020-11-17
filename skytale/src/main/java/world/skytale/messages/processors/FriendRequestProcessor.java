package world.skytale.messages.processors;

import com.google.protobuf.InvalidProtocolBufferException;

import world.skytale.MessageProcessingException;
import world.database.ContactsHandler;
import world.database.DatabaseHandler;
import world.database.FriendRequestHandler;
import world.database.ProfilePageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Account;
import world.skytale.model.sendable.FriendRequest;
import world.skytale.model.implementations.ID;
import world.skytale.model.ProfilePage;
import world.skytale.model.implementations.FriendRequestImp;
import world.skytale.model.proto.ProfilePageProto;

public class FriendRequestProcessor  implements MessageProcessor{


    private final Account userAccount;
    private final FriendRequestHandler friendRequestHandler;
    private final ProfilePageHandler profilePageHandler;
    private final ContactsHandler contactsHandler;

    public FriendRequestProcessor(DatabaseHandler databaseHandler) {
        this.userAccount = databaseHandler.getAccountProvider().getCurrentAccount();
        this.friendRequestHandler = databaseHandler.getFriendRequestHandler();
        this.profilePageHandler = databaseHandler.getProfilePageHandler();
        this.contactsHandler = databaseHandler.getContactsHandler();
    }

    @Override
    public void processIncoming(VeryfiedMessage message) throws InvalidProtocolBufferException, MessageProcessingException {
        Messages.FriendRequest friendRequestProto = Messages.FriendRequest.parseFrom(message.getMessageBytes());

        ID senderID = message.getMessageHeader().getSenderID();
        long time = message.getMessageHeader().getTime();
        String reciversEmail = friendRequestProto.getReciversEmail();


        if(!isUserRequestRecipet(reciversEmail))
        {
            throw new MessageProcessingException("IDKN yet");
        }


        ProfilePage profilePage = new ProfilePageProto(friendRequestProto.getProfilePage(), senderID);
        FriendRequest friendRequest = new FriendRequestImp(message.getMessageHeader().getMessageID(),reciversEmail);


        contactsHandler.addContact(message.getSender());
        profilePageHandler.addProfilePage(profilePage);
        friendRequestHandler.addFriendRequest(friendRequest);
    }


    private boolean isUserRequestRecipet(String reciversEmail)
    {
      String userAddress =   userAccount.getUserContact().getAdress();
      return userAddress.toLowerCase().equals(reciversEmail.toLowerCase());
    }


}
