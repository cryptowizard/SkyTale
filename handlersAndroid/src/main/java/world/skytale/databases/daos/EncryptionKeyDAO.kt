package world.skytale.databases.daos

import world.skytale.converters.SecretKeyConventer
import world.skytale.model.AvaiableMessages.EncryptionKey
import world.skytale.model.ID
import javax.crypto.SecretKey


data class EncryptionKeyDAO ( private val senderID : Long, private val time : Long, val secretKey : String) : EncryptionKey {

    constructor(senderID: ID, time: Long, secretKey: SecretKey) : this(senderID.toLong(),time, SecretKeyConventer.toString(secretKey))
    constructor(encryptionKey: EncryptionKey) : this(encryptionKey.senderID, encryptionKey.time, encryptionKey.key)

    override fun getSenderID() : ID
    {
        return ID(senderID);
    }

    override fun getTime(): Long {
        return time;
    }

    override fun getKey(): SecretKey {
        return SecretKeyConventer.fromString(secretKey);
    }
}