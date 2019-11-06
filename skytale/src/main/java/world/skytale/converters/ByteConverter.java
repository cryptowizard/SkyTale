package world.skytale.converters;

import java.io.UnsupportedEncodingException;

/**
 * Byte converter is used mainly to store byte [] as Strings in database or files
 *
 * The class methods should not be used in order to convert String with non standard characters like 'ż' 'ź' 'ł' as they will be lost and replaced with 'ł'
 */
public class ByteConverter {

    public static final String ENCODING = "ISO-8859-15";

    public static String toString(byte [] bytes)
    {
        try {
            return new String(bytes,ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public static byte [] fromString(String string)
    {
        try {
            return string.getBytes(ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
