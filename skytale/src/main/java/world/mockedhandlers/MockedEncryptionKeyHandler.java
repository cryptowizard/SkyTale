package world.mockedhandlers;

import java.util.ArrayList;

import world.database.EncryptionKeyHandler;
import world.database.ItemNotFoundException;
import world.skytale.model.EncryptionKey;
import world.skytale.model.implementations.ID;
import world.skytale.model.implementations.KeyID;

public class MockedEncryptionKeyHandler implements EncryptionKeyHandler {

    ArrayList<EncryptionKey> list = new ArrayList();

    @Override
    public EncryptionKey getEncryptionKey(KeyID keyID) throws ItemNotFoundException {
        int index = getIndexOf(keyID);
        if(index>=0) return list.get(index);
        else throw new ItemNotFoundException("EncryptionKeys", keyID.getSenderID().toString());
    }

    @Override
    public EncryptionKey getEncryptionKeyWithTheLowestType(ID senderID) throws ItemNotFoundException {
        EncryptionKey current = null;

        for (EncryptionKey key : list) {
            if (key.getKeyID().getSenderID().equals(senderID)) {
                if (current == null || current.getKeyID().getKeyType() > key.getKeyID().getKeyType()) current = key;
            }
        }

        if (current == null) throw new ItemNotFoundException("EncryptionKeys", senderID.toString());
        else return current;
    }

    @Override
    public boolean addEncryptionKey(EncryptionKey encryptionKey) {
        return list.add(encryptionKey);
    }

    private int getIndexOf(KeyID keyID)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getKeyID() == keyID) return i;
        }
        return  -1;
    }
}
