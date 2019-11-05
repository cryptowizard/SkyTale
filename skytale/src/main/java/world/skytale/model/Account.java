package world.skytale.model;

import java.security.PrivateKey;

public abstract class Account {

    public abstract Contact getUserContact();
    public abstract PrivateKey getPrivateKey();
}
