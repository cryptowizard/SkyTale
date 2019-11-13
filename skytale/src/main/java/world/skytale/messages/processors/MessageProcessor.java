package world.skytale.messages.processors;

import world.skytale.messages.DownloadedMail;

public interface MessageProcessor {
    void processIncoming(DownloadedMail downloadedMail) throws Exception;

}
