package world.skytale.databases;

import androidx.annotation.Nullable;

import java.io.File;

import world.skytale.model.attachments.FileAttachment;
import world.skytale.model2.Attachment;
import world.skytale.model2.ID;

public class ProfilePictureStore {

    private final String profilePicutresDirectoryPath;

    public static final String [] IMAGE_EXTENSIONS = {"jpeg", "jpg", "png"};

    public ProfilePictureStore(String profilePicutresDirectoryPath) {
        this.profilePicutresDirectoryPath = profilePicutresDirectoryPath;
    }

    @Nullable
    public FileAttachment saveProfilePicture(ID contactID, @Nullable Attachment profilePicture)
    {
        if(profilePicture==null||!isImage(profilePicture.getExtension()))
        {
            return null;
        }

        return new FileAttachment("String xd");

    }


    private void writeFile(String path, byte [] fileBytes)
    {

    }

    @Nullable
    public FileAttachment getProfilePicture(ID contactID)
    {
        for(String extension : IMAGE_EXTENSIONS)
        {
            File tmp = new File(getPicturePath(contactID, extension));
            if(tmp.exists())
            {
                return new FileAttachment(tmp.getAbsolutePath());
            }
        }
        return null;
    }


    private String getPicturePath(ID contactID, String extension)
    {
        return "";
    }

    private boolean isImage(String extension)
    {
       for(String possible : IMAGE_EXTENSIONS)
       {
           if(extension.equals(possible))
           {
               return true;
           }
       }
       return false;
    }
}
