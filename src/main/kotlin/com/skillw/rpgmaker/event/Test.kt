package com.skillw.rpgmaker.event

import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent

object Test {
    @SubscribeEvent
    fun player(event: AsyncPlayerConfigurationEvent) {
        println(event.player)
    }
}
@SubscribeEvent
fun player(event: AsyncPlayerConfigurationEvent) {
    println(event.player)
}