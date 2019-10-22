package world.skytale.converters;

import android.util.Base64;

public class StringConverter {

    public static byte [] toBytes(String string)
    {
        return Base64.decode(string,Base64.DEFAULT);
    }

    public static String fromBytes(byte [] bytes)
    {
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
}
