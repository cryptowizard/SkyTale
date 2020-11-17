package world.skytale.messages;

import java.security.PublicKey;
import java.util.ArrayList;

import world.skytale.MessageProcessingException;
import world.skytale.converters.PublickKeyConverter;
import world.database.ContactsHandler;
import world.skytale.model.Attachment;
import world.skytale.model.Contact;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.ContactImp;

import static world.skytale.messages.builders.MailBuilder.MESSAGE_EXTENSION;
import static world.skytale.messages.builders.MailBuilder.PUBLIC_KEY_EXTENSION;
import static world.skytale.messages.builders.MailBuilder.SIGNATURE_EXTENSION;

public class MessageVerifier {

   private final ContactsHandler contactsHandler;

    public MessageVerifier(ContactsHandler contactsHandler) {
        this.contactsHandler = contactsHandler;
    }

    public VeryfiedMessage veryfieMessage(MessageHeader messageHeader , ArrayList<Attachment> attachments, String senderEmail) throws Exception {


        byte [] message = findAttachmentWithExtension(attachments,MESSAGE_EXTENSION).getFileBytes();
        byte [] signature = findAttachmentWithExtension(attachments, SIGNATURE_EXTENSION).getFileBytes();


        Contact sender = getSender(messageHeader, attachments, senderEmail);

        boolean isSignatureValid  =  MessageSignature.checkSingatureOfMessageandHeadder(messageHeader,message,signature,sender.getPublicKey());
        if(!isSignatureValid)
        {
            throw new MessageProcessingException("Invalid Signature");
        }

        VeryfiedMessage veryfiedMessage = new VeryfiedMessage(messageHeader, message, sender);
        return veryfiedMessage;
    }

    private Contact getSender(MessageHeader messageHeader , ArrayList<Attachment> attachments, String sendersEmail) throws Exception {
        try {
            PublicKey publicKey = PublickKeyConverter.fromBytes(findAttachmentWithExtension(attachments, PUBLIC_KEY_EXTENSION).getFileBytes());
            Contact sender = createContactFromPublicKey(messageHeader,publicKey,sendersEmail);
            return sender;

        }catch ( AttachmentNotFoundException exception)
        {
            return contactsHandler.getContact(messageHeader.getSenderID());
        }
    }

    private Contact createContactFromPublicKey(MessageHeader messageHeader, PublicKey publicKey, String sendersEmail) throws Exception {
        ID  senderID = messageHeader.getSenderID();
        veryfiySenderID(publicKey,senderID);
        return new ContactImp(senderID,publicKey,sendersEmail);
    }

    private void veryfiySenderID(PublicKey publicKey, ID senderIDFromHeader) throws Exception {
        ID idFromPublicKey = ID.PublicKeyID.makeID(publicKey);
        if(!idFromPublicKey.equals(senderIDFromHeader)) throw new Exception("Type is yet to be made");
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
