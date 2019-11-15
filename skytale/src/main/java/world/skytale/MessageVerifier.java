package world.skytale;

import java.io.IOException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import world.skytale.converters.PublickKeyConverter;
import world.skytale.cyphers.RSASignature;
import world.skytale.database.ContactsHandler;
import world.skytale.messages.MessageHeader;
import world.skytale.model.Contact;
import world.skytale.model.attachments.Attachment;

import static world.skytale.MailBuilder.MESSAGE_EXTENSION;
import static world.skytale.MailBuilder.PUBLIC_KEY_EXTENSION;
import static world.skytale.MailBuilder.SIGNATURE_EXTENSION;

public class MessageVerifier {

   private final ContactsHandler contactsHandler;

    public MessageVerifier(ContactsHandler contactsHandler) {
        this.contactsHandler = contactsHandler;
    }

    public VeryfiedMessage veryfieMessage(MessageHeader messageHeader , ArrayList<Attachment> attachments) throws ContactsHandler.ContactNotFoundException, InvalidKeySpecException, MessageProcessingException, IOException, AttachmentNotFoundException {


        byte [] message = findAttachmentWithExtension(attachments,MESSAGE_EXTENSION).getFileBytes();
        byte [] signature = findAttachmentWithExtension(attachments, SIGNATURE_EXTENSION).getFileBytes();

        PublicKey publicKey;
        int contactType = Contact.TYPE_DEFAULT;
        try {
            publicKey = PublickKeyConverter.fromBytes(findAttachmentWithExtension(attachments, PUBLIC_KEY_EXTENSION).getFileBytes());
        }catch ( AttachmentNotFoundException exception)
        {
            Contact contact = contactsHandler.getContact(messageHeader.getSenderID());
            contactType = contact.getContactType();
            publicKey =  contact.publicKey;
        }

        boolean isSignatureValid  = RSASignature.veryfiSignature(publicKey,message,signature);
        if(!isSignatureValid)
        {
            throw new MessageProcessingException("Invalid Signature");
        }

        VeryfiedMessage veryfiedMessage = new VeryfiedMessage(messageHeader, message, contactType);
        return veryfiedMessage;
    }

    private static Attachment findAttachmentWithExtension(ArrayList<Attachment> attachments, String extension) throws AttachmentNotFoundException {
        for(Attachment attachment : attachments)
        {
            if(attachment.getExtension().toLowerCase().equals(extension))
            {
                return attachment;
            }
        }

        throw new AttachmentNotFoundException(extension);
    }

    public static class AttachmentNotFoundException extends  Exception
    {
        public AttachmentNotFoundException(String extension)
        {
            super("Attachment of contactType : "+extension+" was not found");
        }
    }
}
