package world.skytale.databases.files;

import androidx.annotation.Nullable;

import java.io.File;

import world.skytale.databases.model.FileAttachment;
import world.skytale.model.Attachment;
import world.skytale.model.implementations.ID;

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
       return FileAttachment.fromPath("somePath.xd");

    }

    public void removeProfilePictureIfExists(ID contactID)
    {

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
                return FileAttachment.fromPath(tmp.getAbsolutePath());
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
