package world.skytale.messages.processors;


import android.util.Log;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.skytale.MessageProcessingException;
import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.ElipticCurveCypher;
import world.skytale.message.Messages;
import world.skytale.messages.VeryfiedMessage;
import world.skytale.model.Account;
import world.skytale.model.implementations.ID;
import world.skytale.model.proto.EncryptionKeyProto;
import world.skytale.model.EncryptionKey;

public class PostEncryptionKeyProcessor implements MessageProcessor {


    private final Account account;
    private final EncryptionKeyHandler encryptionKeyHandler;

    public PostEncryptionKeyProcessor(Account account, EncryptionKeyHandler encryptionKeyHandler) {
        this.account = account;
        this.encryptionKeyHandler = encryptionKeyHandler;
    }


    @Override
    public void processIncoming(VeryfiedMessage message) throws MessageProcessingException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Messages.PostEncryptionKeys postEncryptionKeys = Messages.PostEncryptionKeys.parseFrom(message.getMessageBytes());
        ID senderID = message.getSender().getID();
        for(ByteString encryptedKey : postEncryptionKeys.getEncryptedEncryptionKeysList())
        {
            byte [] decryptedKey = decryptWithAccountsPrivateKey(ByteConverter.fromBytesString(encryptedKey));
            Messages.EncryptionKey encryptionKeyProto = Messages.EncryptionKey.parseFrom(decryptedKey);
            EncryptionKey encryptionKey = new EncryptionKeyProto(encryptionKeyProto,senderID.toLong());
           Log.i("timebzz",encryptionKey.getKeyID().toString());
           if(canAddEncryptionKey(encryptionKey))
            encryptionKeyHandler.addEncryptionKey(encryptionKey);
           else throw new  MessageProcessingException(message.getMessageHeader(),"Trying to update encryption key with older key", message.getSender().getAdress());
        }
    }

    private Boolean canAddEncryptionKey(EncryptionKey encryptionKey)
    {
        try{
            EncryptionKey oldKey = encryptionKeyHandler.getEncryptionKey(encryptionKey.getKeyID());
            return  oldKey.getTime() < encryptionKey.getTime();
        } catch (ItemNotFoundException e) {
            return true;
        }
    }

    private byte [] decryptWithAccountsPrivateKey(byte [] c) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return ElipticCurveCypher.decrypt(account.getPrivateKey(),c);
    }
}
