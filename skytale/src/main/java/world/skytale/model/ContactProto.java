package world.skytale.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import world.skytale.MessageProcessingException;
import world.skytale.converters.PublickKeyConverter;
import world.skytale.message.Messages;
import world.skytale.model.attachments.ProtoAttachment;
import world.skytale.model2.Attachment;
import world.skytale.model2.Contact;
import world.skytale.model2.ID;

public class ContactProto implements Contact {


    private final Messages.Contact prtoMessage;
    private int contactType = Contact.TYPE_DEFAULT;


    public ContactProto(Messages.Contact prtoMessage, int contactType) throws MessageProcessingException {
        this(prtoMessage);
        this.contactType = contactType;
    }

    public ContactProto(Messages.Contact prtoMessage) {
        this.prtoMessage = prtoMessage;
    }


    @NonNull
    @Override
    public ID getID() {
        return ID.PublicKeyID.makeID(getPublicKey());
    }

    @NonNull
    @Override
    public PublicKey getPublicKey() {
        try {
            return PublickKeyConverter.fromBytes(prtoMessage.getPublicKey().toByteArray());
        } catch (InvalidKeySpecException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public String getAdress() {
        return prtoMessage.getAddress();
    }

    @Nullable
    @Override
    public String getFirstName() {
        return prtoMessage.getFirstName();
    }

    @Nullable
    @Override
    public String getLastName() {
        return prtoMessage.getLastName();
    }

    @Nullable
    @Override
    public Attachment getProfilePicture() {
        return new ProtoAttachment(prtoMessage.getProfilePicture());
    }

    @Override
    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }
}
