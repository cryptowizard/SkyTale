package world.skytale.databases.model;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.Displayable;

public class DisplayableDAO implements Displayable {

    private String [] attachmentsPaths = new String[0];
    private String link = "";
    private String text = "";
    private String configuration="";


    public void setText(String text) {
        this.text = text;
    }

    public void setAttachments(String [] attachments)
    {
        this.attachmentsPaths = attachments;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Attachment> getAttachments() {
        return fromPathList(this.attachmentsPaths);
    }

    public String getLink() {
        return link;
    }

    @Override
    public String getConfiguration() {
        return configuration;
    }

    private static ArrayList<Attachment> fromPathList(String[] filePaths) {
        ArrayList<Attachment> list = new ArrayList<>();
        for (String path : filePaths) {
            FileAttachment fileAttachment = FileAttachment.fromPath(path);
            if (fileAttachment != null) {
                list.add(fileAttachment);
            }
        }
        return list;
    }

    public String [] getAttachmentPaths ()
    {
        return this.attachmentsPaths;
    }

    public void setConfiguration(String configuration){
        this.configuration = configuration;
    }

}
