package world.skytale.proto;

import world.skytale.message.Messages;
import world.skytale.model.ProfilePage;

public class FriendRequestProto  {


    public static Messages.FriendRequest toProto(ProfilePage profilePage, String reciversEmail)
    {
        Messages.ProfilePage protoProfilePage = ProfilePageProto.toProto(profilePage);

        Messages.FriendRequest tmp = Messages.FriendRequest.newBuilder()
                .setProfilePage(protoProfilePage)
                .setReciversEmail(reciversEmail)
                .build();
        return tmp;
    }



}
