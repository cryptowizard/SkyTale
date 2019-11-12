package world.skytale.model;

import com.google.protobuf.ByteString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import world.skytale.database.FilesHandler;
import world.skytale.message.Messages;

public class Attachment  {


    public static String [] saveAttachments(List<Messages.Attachment> attachments, FilesHandler filesHandler)
    {
        String [] paths = new String[attachments.size()];
        for(int i=0;i<paths.length;i++)
        {
            paths[i] = saveAttachment(attachments.get(i),filesHandler);
        }
        return paths;
    }

    public static String saveAttachment(Messages.Attachment attachment, FilesHandler filesHandler)
    {

    return  filesHandler.writeTemporaryFile(attachment.getFileBytes().toByteArray(),attachment.getFileExtension());
    }

    public static ArrayList< Messages.Attachment>  makeAttachments(String [] paths, FilesHandler fileStore) throws IOException {

        ArrayList< Messages.Attachment> attachments = new ArrayList<Messages.Attachment>();
        for(int i=0;i<paths.length;i++)
        {
            attachments.add( makeAttachment(paths[i], fileStore));
        }
        return attachments;
    }

    public static Messages.Attachment makeAttachment (String filePath, FilesHandler filesStorage) throws IOException {
        String fileName=getExtension(filePath);
        byte [] fileBytes = filesStorage.readFileBytes(new File(filePath));

        Messages.Attachment tmp = Messages.Attachment.newBuilder()
                .setFileBytes(ByteString.copyFrom(fileBytes))
                .setFileExtension(fileName)
                .build();
        return tmp;
    }

    /**
     * @ToDO hmmm ccddx
     * @param filePath
     * @return  file extension with '.'
     */
    public static String getExtension(String filePath)
    {
        return "";
    }
}
