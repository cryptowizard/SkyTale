package world.skytale.converters;

import java.io.UnsupportedEncodingException;

public class StringConverter {

    public static final String ENCODING = "ISO-8859-15";

    public static byte [] toBytes(String string)
    {
        try {
            return string.getBytes(ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String fromBytes(byte [] bytes)
    {
        try {
            return new String(bytes,ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
