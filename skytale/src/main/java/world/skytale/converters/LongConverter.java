package world.skytale.converters;


import java.math.BigInteger;


public class LongConverter {

    public static byte[] toBytes(long number) {
            BigInteger bigInteger = BigInteger.valueOf(number);
            return bigInteger.toByteArray();
    }

    public static long fromBytes(byte[] bytes) {
            BigInteger bigInteger = new BigInteger(bytes);
            return bigInteger.longValue();
    }

    public static String toBase32String(long number)
    {
        BigInteger bigInteger = BigInteger.valueOf(number);
        return bigInteger.toString(32);
    }

    public static long fromBase32String(String string)
    {

        return new BigInteger(string,32).longValue();
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String toBase64String(long number)
//    {
//
//       return Base64.getEncoder().encodeToString(toBytes(number));
//     //   BigInteger bigInteger = BigInteger.valueOf(number);
//       // return bigInteger.toString(32);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static long fromBase64String(String string)
//    {
//
//        return fromBytes(Base64.getDecoder().decode(string));
//    }








}
