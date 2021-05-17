package world.skytale.databases.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import world.skytale.model.Attachment;
import world.skytale.utils.FileUtils;

public class FileAttachment  implements Attachment {
    
    private String filePath;


    public static FileAttachment fromPath(String filePath)
    {
        if(filePath==null||filePath.length()==0)
        {
            return null;
        }
        else return new FileAttachment(filePath);
    }

    private FileAttachment(String filePath)
    {
        this.filePath=filePath;
    }

    @Override
    public byte[] getFileBytes() throws IOException {
        return readFileBytes();
    }

    @Override
    public String getExtension() {
         return FileUtils.getExtension(filePath);
    }

    public String getFilePath()
    {
        return filePath;
    }
    
    
    private byte[] readFileBytes() throws IOException {

        int end = filePath.lastIndexOf("/");
        String str1 = filePath.substring(0, end);
        String str2 = filePath.substring(end+1);
        File source = new File(str1, str2);
        long a= source.length();
        int size = Integer.valueOf(Long.toString(a));
        FileInputStream inp = new FileInputStream(source);
        byte [] qx = new byte [size];
        inp.read(qx);
        return qx;
    }
}
