package com.skillw.rpgmaker.module.fight.default

import com.skillw.rpgmaker.module.fight.BaseFightData
import net.kyori.adventure.text.Component
import net.minestom.server.entity.Entity
import net.minestom.server.entity.LivingEntity
import net.minestom.server.entity.Player
import net.minestom.server.entity.damage.Damage
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry

class DefaultFightData(
    private val attacker: Entity?,
    private val defender: Entity?,
    private val damageType: DynamicRegistry.Key<DamageType> = DamageType.MAGIC
) : BaseFightData(attacker, defender) {

    private var finalValue = 0.0F
    val damage: Damage = damage()

    override fun calMessage() {
        if (attacker is Player) {
            attacker.sendMessage(Component.text("你造成了${damage.amount}点伤害"))
        }
    }

    override fun damage(): Damage {
        return Damage(damageType, defender, attacker, null, finalValue + 10F)
    }

    override fun runDamage() {
        defender?.let Let@{
            (defender as? LivingEntity)?.damage(damage) ?: return@Let
        }
        calMessage()
    }

}