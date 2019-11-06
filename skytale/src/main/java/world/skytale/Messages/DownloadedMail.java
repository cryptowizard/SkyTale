package world.skytale.Messages;


import java.io.File;

/**
 *  Class DownloadedMail represents email message with downloaded attachments as list of files
 */
public class DownloadedMail {

    private String sender;
    private String title;
    private String message;
    private File [] attachments  ;

    public DownloadedMail(String t, String m, File [] atachments)
    {
        this.title=t;
        this.message=m;
        this.attachments=atachments;
    }


}
