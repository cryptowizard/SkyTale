package world.skytale.utils;

public class FileUtils {


    /**
     * @ToDO hmmm ccddx
     * @param path
     * @return  file extension without '.'
     */
    public static String getExtension(String path)
    {
        int i = path.lastIndexOf(".");
        return path.substring(i+1,path.length());
    }
}
