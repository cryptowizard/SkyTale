package world.skytale.model2;

import java.security.PrivateKey;

import world.skytale.model.ContactImp;

public interface Account {
     ContactImp getUserContact();
     PrivateKey getPrivateKey();
}
