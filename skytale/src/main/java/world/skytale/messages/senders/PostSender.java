package world.skytale.messages.senders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import world.skytale.messages.DownloadedMail;

public class PostSender extends MessageSender {
    @Override
    protected boolean addToDatabase() {
        return false;
    }

    @Override
    protected boolean removeFromDatabase() {
        return false;
    }

    @Override
    protected DownloadedMail buildDownloadedMail() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        return null;
    }

    @Override
    protected String getRecipients() {
        return null;
    }
}
