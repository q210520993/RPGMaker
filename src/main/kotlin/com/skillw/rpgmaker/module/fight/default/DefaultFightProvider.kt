package com.skillw.rpgmaker.module.fight.default

import com.skillw.rpgmaker.module.fight.BaseFightData
import com.skillw.rpgmaker.module.fight.IFightProvider
import net.minestom.server.entity.Entity
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry

object DefaultFightProvider: IFightProvider {

    override fun calcDamage(attacker: Entity?, defender: Entity?, damageType: DynamicRegistry.Key<DamageType>): BaseFightData {
        val data = DefaultFightData(attacker, defender)
        data.runDamage()
        return data
    }

}