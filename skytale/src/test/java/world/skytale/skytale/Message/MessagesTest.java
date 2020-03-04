package world.skytale.skytale.Message;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.junit.Test;

import java.security.PublicKey;

import world.skytale.cyphers.AccountKey;
import world.skytale.message.Messages;
import world.skytale.model.proto.ContactProto;

import static org.junit.Assert.assertEquals;

public class MessagesTest {


    @Test
    public void testProtos() throws InvalidProtocolBufferException {


        String adress = "msdskytale@gmail.com";


        PublicKey publicKey = AccountKey.generateKeyPair().getPublic();

        ByteString byteString = ByteString.copyFrom(publicKey.getEncoded());

        Messages.Contact contact1 = Messages.Contact.newBuilder()
                .setAddress(adress)
                .setPublicKey(byteString)
                .build();





        Messages.Contact contact2 = Messages.Contact.parseFrom(contact1.toByteArray());

        ContactProto contactProto = new ContactProto(contact2);


        assertEquals(adress,contactProto.getAdress());
    }



}