package com.skillw.rpgmaker.module.fight.default

import com.skillw.rpgmaker.module.fight.BaseFightData
import com.skillw.rpgmaker.utils.ScriptUtil
import net.kyori.adventure.text.Component
import net.minestom.server.entity.Entity
import net.minestom.server.entity.LivingEntity
import net.minestom.server.entity.Player
import net.minestom.server.entity.damage.Damage
import net.minestom.server.entity.damage.DamageType
import net.minestom.server.registry.DynamicRegistry
import kotlin.math.cos
import kotlin.math.sin

class DefaultFightData(
    private val attacker: Entity?,
    private val defender: Entity?,
    private val damageType: DynamicRegistry.Key<DamageType> = DamageType.MAGIC
) : BaseFightData(attacker, defender) {

    private var finalValue = 0.0F
    var damage: Damage = damage()
    var isKnockback: Boolean = true
    var damageResult: Boolean = false

    constructor(attacker: Entity?, defender: Entity?, damage: Damage): this(attacker, defender, damage.type) {
        this.damage = damage
    }

    override fun calMessage() {
        if (attacker is Player) {
            attacker.sendMessage(Component.text("你造成了${damage.amount}点伤害"))
        }
    }

    override fun damage(): Damage {
        return Damage(damageType, defender, attacker, null, finalValue + 10F)
    }

    override fun runDamage(): Boolean {
        defender?.let Let@{
            (defender as? LivingEntity)?.let Let2@{ defenderLet ->
                damageResult = defenderLet.damage(damage)
                if (isKnockback && damageResult) {
                    //原版手撸强度为0.4f, 0.017453292
                    attacker?.let { att ->
                        it.takeKnockback(
                            0.4f, sin(att.position.yaw.times(0.017453292)),
                            -cos(att.position.yaw * 0.1745292)
                        )
                    }
                }
            } ?: return@Let
        }
        if (damageResult) {
            calMessage()
        }
        return damageResult
    }

}