package world.skytale.databases.daos

import world.skytale.converters.SecretKeyConventer
import world.skytale.model.ID
import world.skytale.model.MessageID
import world.skytale.model.sendable.EncryptionKey
import javax.crypto.SecretKey


data class EncryptionKeyDAO ( private val senderID : Long, private val time : Long, val secretKey : String) : EncryptionKey {

    constructor(senderID: ID, time: Long, secretKey: SecretKey) : this(senderID.toLong(), time, SecretKeyConventer.toString(secretKey))
    constructor(encryptionKey: EncryptionKey) : this(encryptionKey.messageID.senderID, encryptionKey.messageID.time, encryptionKey.key)

    fun getSenderID(): ID {
        return ID(senderID);
    }

    fun getTime(): Long {
        return time;
    }

    override fun getKey(): SecretKey {
        return SecretKeyConventer.fromString(secretKey);
    }

    override fun getMessageID(): MessageID {
        return MessageID(ID(senderID), time)
    }

}