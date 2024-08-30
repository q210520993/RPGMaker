package com.skillw.rpgmaker.module.fight

import com.skillw.rpgmaker.RPGMaker
import com.skillw.rpgmaker.core.handlers.annotations.CoreHook
import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.core.handlers.hook.ICoreHook
import com.skillw.rpgmaker.module.fight.default.DefaultFightProvider
import net.minestom.server.entity.damage.Damage
import net.minestom.server.event.entity.EntityAttackEvent

@CoreHook
object FightManager: ICoreHook {

    var provider: IFightProvider = DefaultFightProvider

    override fun hook(rpgMaker: RPGMaker) {
        provider.init()
    }

}

@SubscribeEvent(priority = EventPriority.SERVER)
fun attack(event: EntityAttackEvent) {
    FightManager.provider.calcDamage(
        event.entity, event.target, Damage.fromEntity(event.target, 5f)
    )
}