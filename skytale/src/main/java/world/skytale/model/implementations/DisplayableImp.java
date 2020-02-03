package world.skytale.model.implementations;

import java.util.ArrayList;

import world.skytale.model.Attachment;
import world.skytale.model.Displayable;

public class DisplayableImp implements Displayable {

    private String text ="";
    private ArrayList<Attachment> attachments = new ArrayList<Attachment>();
    private String link = "";
    private String configuration="";

    public DisplayableImp()
    {

    }

    public DisplayableImp(String text, ArrayList<Attachment> attachments, String link, String configuration) {
        this.text = text;
        this.attachments = attachments;
        this.link = link;
        this.configuration = configuration;
    }

    public void addAttachment(Attachment attachment)
    {
        this.attachments.add(attachment);
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
