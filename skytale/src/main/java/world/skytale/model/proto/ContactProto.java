package world.skytale.model.proto;

import androidx.annotation.NonNull;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import world.skytale.MessageProcessingException;
import world.skytale.converters.PublickKeyConverter;
import world.skytale.message.Messages;
import world.skytale.model.Contact;
import world.skytale.model.ID;

public class ContactProto implements Contact {


    private final Messages.Contact prtoMessage;


    public ContactProto(Messages.Contact prtoMessage, int contactType) throws MessageProcessingException {
        this(prtoMessage);
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

    @Override
    public boolean isFriend() {
        return false;
    }

    @Override
    public boolean isFollower() {
        return false;
    }

    @Override
    public boolean isObserved() {
        return false;
    }

}
