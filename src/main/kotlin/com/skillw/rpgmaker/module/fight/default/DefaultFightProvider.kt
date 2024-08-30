package com.skillw.rpgmaker.module.fight.default

import com.skillw.rpgmaker.module.fight.BaseFightData
import com.skillw.rpgmaker.module.fight.IFightProvider
import com.skillw.rpgmaker.utils.ConfigUtil
import com.skillw.rpgmaker.utils.ResourceUtil.extractResource
import net.minestom.server.entity.Entity
import net.minestom.server.entity.damage.Damage
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import taboolib.module.configuration.Configuration
import java.io.File

object DefaultFightProvider: IFightProvider {

    lateinit var configuration: Configuration
    lateinit var file: File
    lateinit var context: Context

    override fun init() {
    }

    override fun calcDamage(
        attacker: Entity?, defender: Entity?,
        damageType: DynamicRegistry.Key<DamageType>
    ): BaseFightData {
        val data = DefaultFightData(attacker, defender, damageType)
        data.runDamage()
        return data
    }

    override fun calcDamage(
        attacker: Entity?,
        defender: Entity?,
        damage: Damage
    ): BaseFightData {
        val data = DefaultFightData(attacker, defender, damage)
        data.runDamage()
        return data
    }

}