package com.skillw.rpgmaker.module.fight

import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.module.fight.default.DefaultFightProvider
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.event.entity.EntityAttackEvent
import net.minestom.server.event.player.PlayerSpawnEvent

object FightManager {

    var provider: IFightProvider = DefaultFightProvider

}

@SubscribeEvent(priority = EventPriority.SERVER)
fun attack(event: EntityAttackEvent) {
    FightManager.provider.calcDamage(event.entity, event.target, DamageType.PLAYER_ATTACK)
}