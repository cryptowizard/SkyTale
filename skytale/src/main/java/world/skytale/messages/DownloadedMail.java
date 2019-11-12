package world.skytale.messages;


import java.util.ArrayList;

/**
 *  Class DownloadedMail represents email message with downloaded attachments as list of files
 */
public class DownloadedMail {

    private String title;
    private String message;
    private ArrayList<String> attachmentPaths  ;


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

    public ArrayList<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void  addAttachment(String attachmentPath)
    {
        this.attachmentPaths.add(attachmentPath);
    }
    public void setAttachmentPaths(ArrayList<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }



    public DownloadedMail()
    {
        this.message = "";
        this.title = "";
        this.attachmentPaths = new ArrayList<String>();
    }

    public DownloadedMail(String t, String m, ArrayList<String> atachments)
    {
        this.title=t;
        this.message=m;
        this.attachmentPaths=atachments;
    }


}
