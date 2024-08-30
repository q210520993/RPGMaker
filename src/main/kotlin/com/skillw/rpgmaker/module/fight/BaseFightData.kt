package com.skillw.rpgmaker.module.fight

import net.minestom.server.entity.Entity
import net.minestom.server.entity.damage.Damage

abstract class BaseFightData(attacker: Entity?, defender: Entity?) {

    abstract fun calMessage()
    abstract fun damage(): Damage
    abstract fun runDamage(): Boolean

}