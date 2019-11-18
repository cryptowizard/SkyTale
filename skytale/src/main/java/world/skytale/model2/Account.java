package world.skytale.model2;

import java.security.PrivateKey;

public interface Account {
     Contact getUserContact();
     PrivateKey getPrivateKey();
}
