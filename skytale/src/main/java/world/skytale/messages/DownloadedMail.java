package world.skytale.messages;


import java.util.ArrayList;

import world.skytale.model.Attachment;

/**
 *  Class DownloadedMail represents email message with downloaded attachments as list of files
 */
public class DownloadedMail {

    private String title;
    private String message;
    private ArrayList<Attachment> attachments  ;


    public DownloadedMail()
    {
        this.message = "";
        this.title = "";
        this.attachments = new ArrayList<Attachment>();
    }

    public DownloadedMail(String title, ArrayList<Attachment> attachments) {
       super();
        this.title = title;
        this.attachments = attachments;
    }

    public DownloadedMail(String t, String m, ArrayList<Attachment> atachments)
    {
        this.title=t;
        this.message=m;
        this.attachments=atachments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void  addAttachment(Attachment attachment)
    {
        this.attachments.add(attachment);
    }
    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public int getNumberOfAttachments()
    {
        return attachments.size();
    }




}
