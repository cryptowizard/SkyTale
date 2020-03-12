package world.skytale.messages;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import world.skytale.converters.ByteConverter;
import world.skytale.cyphers.Sha256;
import world.skytale.cyphers.ECSignature;

public class MessageSignature {

    public static byte [] singMessageWithHeader(MessageHeader messageHeader, byte [] message, PrivateKey privateKey) {
        byte [] headerAndMessageHash = addHeaderAndMessageHashes(messageHeader,message);
        try {
            return ECSignature.sign(privateKey, headerAndMessageHash);
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e);
        }
    }


    public static boolean checkSingatureOfMessageandHeadder(MessageHeader messageHeader, byte [] message, byte [] signature, PublicKey publicKey)
    {
        byte [] headerAndMessageHash = addHeaderAndMessageHashes(messageHeader,message);
        return ECSignature.veryfiSignature(publicKey,headerAndMessageHash,signature);
    }


    private static byte [] addHeaderAndMessageHashes(MessageHeader messageHeader, byte [] message)
    {
        byte [] headerBytes = getMessageHeaderBytes(messageHeader);

        byte [] messageHash = Sha256.hash(message);
        byte [] headerHash = Sha256.hash(headerBytes);

        return concat(messageHash, headerHash);
    }


    private static byte [] getMessageHeaderBytes(MessageHeader messageHeader) {
        String messageTitle = messageHeader.makeTitle();
        byte[] titleBytes = ByteConverter.fromString(messageTitle);
        return titleBytes;
    }

    private static byte[] concat(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }

}
