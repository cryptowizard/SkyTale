package world.skytale.cyphers;

import java.util.Arrays;

import world.skytale.converters.LongConverter;
import world.skytale.model.implementations.MessageID;

public class IvVectorGenerator {

    public static byte [] generateIvVector(MessageID messageID)
    {
        long xor = messageID.getTime() ^ messageID.getSenderID().toLong();
        byte [] xoredBytes = LongConverter.toBytes(xor);
        byte [] hash = Sha256.hash(xoredBytes);
        return Arrays.copyOf(hash,16);
    }

}
