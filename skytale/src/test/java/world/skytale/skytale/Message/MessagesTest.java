package world.skytale.skytale.Message;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.junit.Test;

import java.security.PublicKey;

import world.skytale.message.Messages;
import world.skytale.model.Contact;
import world.skytale.cyphers.AccountKey;

import static org.junit.Assert.assertEquals;

public class MessagesTest {


    @Test
    public void testProtos() throws InvalidProtocolBufferException {


        PublicKey publicKey = AccountKey.generateKeyPair().getPublic();
        Contact  contact = new Contact(publicKey,"Maciek","MSD","MSD@gmail.com");
        ByteString byteString = ByteString.copyFrom(contact.publicKey.getEncoded());

        Messages.Contact contact1 = Messages.Contact.newBuilder()
                .setAddress(contact.address)
                .setPublicKey(byteString)
                .setFirstName(contact.firstName)
                .build();





        Messages.Contact contact2 = Messages.Contact.parseFrom(contact1.toByteArray());

        Contact contact3 = new Contact(AccountKey.fromBytes(contact2.getPublicKey().toByteArray()),contact2.getFirstName(),contact2.getLastName(),contact2.getAddress());


        assertEquals(contact.contactID,contact3.contactID);






    }



}