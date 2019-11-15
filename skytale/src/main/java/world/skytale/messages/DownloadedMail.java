package world.skytale.messages;


import java.util.ArrayList;

import world.skytale.model.attachments.Attachment;

/**
 *  Class DownloadedMail represents email message with downloaded attachments as list of files
 */
public class DownloadedMail {

    private String title;
    private String message;
    private ArrayList<Attachment> attachmentPaths  ;


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

    public ArrayList<Attachment> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void  addAttachment(Attachment attachment)
    {
        this.attachmentPaths.add(attachment);
    }
    public void setAttachmentPaths(ArrayList<Attachment> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }



    public DownloadedMail()
    {
        this.message = "";
        this.title = "";
        this.attachmentPaths = new ArrayList<Attachment>();
    }

    public DownloadedMail(String t, String m, ArrayList<Attachment> atachments)
    {
        this.title=t;
        this.message=m;
        this.attachmentPaths=atachments;
    }


}
