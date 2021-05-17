package world.skytale.databases.model

import world.skytale.converters.SecretKeyConventer
import world.skytale.model.EncryptionKey
import world.skytale.model.implementations.KeyID
import javax.crypto.SecretKey

data class EncryptionKeyDAO(private val senderID : Long, private val keyType : Int, private val key : SecretKey, private val time : Long) : EncryptionKey {
    override fun getKeyID(): KeyID {
        return KeyID(senderID, keyType)
    }

    override fun getTime(): Long {
        return time
    }

    override fun getKey(): SecretKey {
       return key
    }

    fun getSecretKeyString() : String = SecretKeyConventer.toString(key)

    constructor(encryptionKey: EncryptionKey) : this(encryptionKey.keyID.senderID.toLong(),encryptionKey.keyID.keyType, encryptionKey.key, encryptionKey.time)
    constructor(keyID: KeyID,key : SecretKey, time: Long) : this(keyID.senderID.toLong(), keyID.keyType, key, time)
}