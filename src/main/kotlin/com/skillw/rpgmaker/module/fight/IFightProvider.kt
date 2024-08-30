package com.skillw.rpgmaker.module.fight

import com.skillw.rpgmaker.utils.ResourceUtil.extractResource
import net.minestom.server.entity.Entity
import net.minestom.server.entity.damage.Damage
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry

interface IFightProvider {

    fun init() {
        //TODO
    }

    fun calcDamage(
        attacker: Entity?,
        defender: Entity?,
        damageType: DynamicRegistry.Key<DamageType>
    ): BaseFightData

    fun calcDamage(
        attacker: Entity?,
        defender: Entity?,
        damage: Damage
    ): BaseFightData

}