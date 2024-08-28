package com.skillw.rpgmaker.module.fight

import net.minestom.server.entity.Entity
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry

interface IFightProvider {

    fun calcDamage(
        attacker: Entity?,
        defender: Entity?,
        damageType: DynamicRegistry.Key<DamageType>
    ): BaseFightData

}