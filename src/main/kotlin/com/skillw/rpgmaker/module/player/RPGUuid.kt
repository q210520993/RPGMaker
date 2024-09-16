package com.skillw.rpgmaker.module.player

import net.minestom.server.network.UuidProvider
import net.minestom.server.network.player.PlayerConnection
import java.nio.charset.StandardCharsets
import java.util.*

object RPGUuid: UuidProvider {

    override fun provide(playerConnection: PlayerConnection, username: String): UUID {
       return UUID.nameUUIDFromBytes(username.toByteArray(StandardCharsets.UTF_8))
    }

}