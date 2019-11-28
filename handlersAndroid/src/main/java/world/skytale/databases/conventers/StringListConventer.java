package world.skytale.databases.conventers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConventer {

    public static final String SPLIT_STRING = "#;";

    public static String toString(List<String> list)
    {
        String tmp = "";
        if(list==null || list.size()==0)
        {
            return tmp;
        }

        for(String string : list)
        {
            tmp += SPLIT_STRING + string;
        }
        return tmp.substring(SPLIT_STRING.length());
    }


    public static String [] toArray(String string)
    {
        return string.split(SPLIT_STRING);
    }
    public static ArrayList<String> fromString(String string)
    {
        ArrayList<String> tmp = new ArrayList<String>();
        String [] array = toArray(string);
        tmp.addAll(Arrays.asList(array));
        return tmp;

    }
}
