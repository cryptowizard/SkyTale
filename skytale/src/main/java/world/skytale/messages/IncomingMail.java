package world.skytale.messages;

import java.util.ArrayList;

import world.skytale.model.attachments.Attachment;

public class IncomingMail extends DownloadedMail {

    private final String sendersEmail;


    public IncomingMail(String sendersEmail) {
        this.sendersEmail = sendersEmail;
    }

    public IncomingMail(String title, ArrayList<Attachment> attachments, String sendersEmail) {
        super(title, attachments);
        this.sendersEmail = sendersEmail;
    }


    public IncomingMail(String t, String m, ArrayList<Attachment> atachments, String sendersEmail) {
        super(t, m, atachments);
        this.sendersEmail = sendersEmail;
    }

    public String getSendersEmail()
    {
        return sendersEmail;
    }


}
