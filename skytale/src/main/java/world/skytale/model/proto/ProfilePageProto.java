package world.skytale.model.proto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import world.skytale.message.Messages;
import world.skytale.model.Attachment;
import world.skytale.model.implementations.ID;
import world.skytale.model.ProfilePage;

public class ProfilePageProto implements ProfilePage {


    private final Messages.ProfilePage protoMessage;
    private final ID contactID;


    public ProfilePageProto(Messages.ProfilePage protoMessage, ID contactID) {
        this.protoMessage = protoMessage;
        this.contactID = contactID;
    }


    public static Messages.ProfilePage toProto(ProfilePage profilePage)
    {
       Messages.ProfilePage  tmp =  Messages.ProfilePage.newBuilder()
                .addAllProfileLinks(profilePage.getProfileLinks())
                .setUserName(profilePage.getUsername())
                .setProfileDescription(profilePage.getDescription())
                .setProfilePicture(ProtoAttachment.toProtoAttachment(profilePage.getProfilePicture()))
                .build();

       return tmp;
    }
    @NonNull
    @Override
    public ID getConstactID() {
        return contactID;
    }

    @NonNull
    @Override
    public String getUsername() {
        return protoMessage.getUserName();
    }

    @Nullable
    @Override
    public Attachment getProfilePicture() {
        return new ProtoAttachment(protoMessage.getProfilePicture());
    }

    @Override
    public String getDescription() {
        return protoMessage.getProfileDescription();
    }

    @Override
    public List<String> getProfileLinks() {
        return protoMessage.getProfileLinksList();
    }
}
