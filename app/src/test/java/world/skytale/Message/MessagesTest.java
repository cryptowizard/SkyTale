package world.skytale.Message;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;

import org.junit.Test;

import java.io.IOException;
import java.security.PublicKey;

import world.skytale.converters.StringConverter;
import world.skytale.cyphers.AccountKey;
import world.skytale.model.Contact;

import static org.junit.Assert.*;

public class MessagesTest {


    @Test
    public void testProtos() throws InvalidProtocolBufferException {


        PublicKey publicKey = AccountKey.generateKeyPair().getPublic();
        Contact  contact = new Contact(publicKey,"Maciek","MSD","MSD@gmail.com");
        ByteString byteString = ByteString.copyFrom(contact.getPublicKey().getEncoded());

        Messages.Contact contact1 = Messages.Contact.newBuilder()
                .setAddress(contact.getAbbess())
                .setPublicKey(byteString)
                .setFirstName(contact.getFirstName())
                .build();





        Messages.Contact contact2 = Messages.Contact.parseFrom(contact1.toByteArray());

        Contact contact3 = new Contact(AccountKey.fromBytes(contact2.getPublicKey().toByteArray()),contact2.getFirstName(),contact2.getLastName(),contact2.getAddress());


        assertEquals(contact.getContactID(),contact3.getContactID());






    }



}