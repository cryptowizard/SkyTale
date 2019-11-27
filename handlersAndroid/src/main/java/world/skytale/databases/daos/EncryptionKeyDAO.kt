package world.skytale.databases.daos

import world.skytale.model2.ID
import javax.crypto.SecretKey

data class EncryptionKey (val senderID : ID, val time : Long, val secretKey : SecretKey) {
}