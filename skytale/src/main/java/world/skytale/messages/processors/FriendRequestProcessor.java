package world.skytale.messages.processors;

import com.google.protobuf.InvalidProtocolBufferException;

import world.skytale.MessageProcessingException;
import world.skytale.database.FriendRequestHandler;
import world.skytale.database.ProfilePageHandler;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Account;
import world.skytale.model.AvaiableMessages.FriendRequest;
import world.skytale.model.ID;
import world.skytale.model.ProfilePage;
import world.skytale.proto.FriendRequestImp;
import world.skytale.proto.ProfilePageProto;

public class FriendRequestProcessor  implements MessageProcessor{


    private final Account userAccount;
    private final FriendRequestHandler friendRequestHandler;
    private final ProfilePageHandler profilePageHandler;

    private FriendRequestProcessor(Account userAccount, FriendRequestHandler friendRequestHandler, ProfilePageHandler profilePageHandler) {
        this.userAccount = userAccount;
        this.friendRequestHandler = friendRequestHandler;
        this.profilePageHandler = profilePageHandler;
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
        FriendRequest friendRequest = new FriendRequestImp(senderID,time,reciversEmail);

        profilePageHandler.addProfilePage(profilePage);
        friendRequestHandler.addFriendRequest(friendRequest);
    }


    private boolean isUserRequestRecipet(String reciversEmail)
    {
      String userAddress =   userAccount.getUserContact().getAdress();
      return userAddress.toLowerCase().equals(reciversEmail.toLowerCase());
    }


}
