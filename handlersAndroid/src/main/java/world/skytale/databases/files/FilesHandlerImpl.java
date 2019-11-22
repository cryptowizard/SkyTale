package world.skytale.databases.files;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import world.skytale.model2.Attachment;
import world.skytale.model2.AttachmentFactory;

public class FilesHandlerImpl implements world.skytale.database.FilesHandler, AttachmentFactory {


    private static FilesHandlerImpl instance;


    public String [] saveAttachments(ArrayList<Attachment> attachments)  {
        ArrayList<String> paths = new ArrayList<String >();
        if(attachments==null)
        {
            return new String[0];
        }
        for(Attachment attachment : attachments)
        {
            try {
                String path = saveAttachment(attachment);
                paths.add(path);
            }
            catch (IOException exce)
            {
                exce.printStackTrace();
            }
        }
        return (String[]) paths.toArray();
    }

    public String saveAttachment(Attachment attachment) throws IOException {
        if(attachment==null)
        {
            return "";
        }
       return this.saveFile(attachment.getFileBytes(),attachment.getExtension());
    }



    public void deleteFile(String filePath)
    {

    }

    public static FilesHandlerImpl getInstance(Context context) throws StoragePermissionDeniedException
    {
        if(instance == null)
        {
            instance = new FilesHandlerImpl(context);
        }
        return instance;
    }

    private FilesHandlerImpl(Context context) throws StoragePermissionDeniedException
    {
        if(!checkStoragePermission(context))
        {
            throw new  StoragePermissionDeniedException();
        }
        makeFolders();
    }

    private   boolean checkStoragePermission(Context context)
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

    private void makeFolders()
    {
        File tmp = new File(getLocalStorageFolderPath());
        if(!tmp.exists())
        {
            tmp.mkdir();

        }
        File tmp2 = new File(getTemporaryFolderPath());
        if(!tmp2.exists())
        {
            tmp2.mkdir();
        }

        if(!(tmp2.exists()&&tmp.exists()))
        {
            makeFolders();
        }
    }



    public byte[] readFileBytes(String path) throws IOException {

        int end = path.lastIndexOf("/");
        String str1 = path.substring(0, end);
        String str2 = path.substring(end+1);
        File source = new File(str1, str2);
        long a= source.length();
        int size = Integer.valueOf(Long.toString(a));
        FileInputStream inp = new FileInputStream(source);
        byte [] qx = new byte [size];
        inp.read(qx);

        return qx;
    }



    public String writeTemporaryFile(byte[] fileBytes, String extension) throws IOException {
        String path = getTemporaryFolderPath()+"/"+generateTemporaryFileName(extension);
        writeFile(fileBytes,path);
        return path;
    }


    public String saveFile(byte[] fileBytes, String extension) throws IOException {
        String path = getLocalStorageFolderPath()+"/"+generateTemporaryFileName(extension);
        writeFile(fileBytes,path);
        return path;
    }

    private String generateTemporaryFileName(String extension)
    {
        String name = new Date().getTime()+"."+extension;
        return name;
    }

    private String generatePermanentFileName(String extension)
    {
        return generateTemporaryFileName(extension);
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

    public static String getLocalStorageFolderPath()
    {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getParentFile().getAbsolutePath();
        ////tw.setText(path);


        return path+"/xFreeMedia";
    }

    /*
    Method getTemporaryFolderPath() returns a path for files that should be deleted after being processed
    Like Attachments that are deleted after being put into database
    Or attachments that are saved on local Storage before being  send
     */
    public static String getTemporaryFolderPath()
    {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getParentFile().getAbsolutePath();



        return path+"/xFreeMedia/tmp";

    }


    public static String getfileName(String path)
    {
        int i = path.lastIndexOf("/");
        return path.substring(i+1,path.length());
    }

    public static String getExtension(String path)
    {
        int i = path.lastIndexOf(".");
        return path.substring(i,path.length());
    }

    @Override
    public Attachment makeAttachment(String extension, byte[] fileBytes) throws IOException {
        return FileAttachment.fromPath(saveFile(fileBytes,extension));
    }


    public static  class StoragePermissionDeniedException extends Exception
    {
        public StoragePermissionDeniedException()
        {
            super("Permission denied");
        }
    }
}
