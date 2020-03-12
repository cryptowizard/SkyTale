package world.skytale.databases.files;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import world.skytale.model.Attachment;

public class  FilesHandler {

    public static String getStorageDirectory (Context context)
    {
        return context.getFilesDir().getAbsolutePath();
    }

    public static String getTemporaryStorageDirectory (Context context)
    {
        return  getStorageDirectory(context)+"/tmp";
    }


    private static FilesHandler instance;
    String storageDirectory;
    String temporaryStorageDirectory;


    public static FilesHandler getInstance(Context context) throws StoragePermissionDeniedException {
        if(instance==null) instance = new FilesHandler(context);
        return instance;
    }

    private FilesHandler(Context context) throws StoragePermissionDeniedException {

        storageDirectory = getStorageDirectory(context);
        temporaryStorageDirectory = getTemporaryStorageDirectory(context);
        if(!checkStoragePermission(context))
        {
            throw new StoragePermissionDeniedException();
        }
        if(!new File(getTemporaryStorageDirectory(context)).exists())
            makeFolders(context);
    }
    private static  boolean checkStoragePermission(Context context)
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    private  boolean makeFolders(Context context)
    {
        this.storageDirectory = getStorageDirectory(context);
        this.temporaryStorageDirectory=getTemporaryStorageDirectory(context);
        return new File(getTemporaryStorageDirectory(context)).mkdirs();
    }

    public String saveAttachment(Attachment attachment) throws IOException {
        String filePath = storageDirectory+"/"+generateNewFileName()+"."+attachment.getExtension();
        new File(filePath).mkdirs();
        writeFile(attachment.getFileBytes(),filePath);
        return filePath;
    }

    public String saveTemporaryAttachment(Attachment attachment) throws IOException {
        String filePath = temporaryStorageDirectory+"/"+generateNewFileName()+"."+attachment.getExtension();
        writeFile(attachment.getFileBytes(),filePath);
        return filePath;
    }

    public boolean removeFile(String filepath)
    {
        return new File(filepath).delete();
    }

    public String generateNewFileName()
    {
        return ""+new Date().getTime();
    }


    public static String getfileName(String path)
    {
        int i = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        return path.substring(i+1,end);
    }

    public static String getExtension(String path)
    {
        int i = path.lastIndexOf(".");
        return path.substring(i+1,path.length());
    }

    private void writeFile(byte [] bytes, String path) throws IOException {
        File yourFile = new File(path);
        yourFile.delete();
        yourFile = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(yourFile));
        bos.write(bytes);
        bos.flush();
        bos.close();
    }

    public static  class StoragePermissionDeniedException extends Exception
    {
        public StoragePermissionDeniedException()
        {
            super("Permission denied");
        }
    }

}
