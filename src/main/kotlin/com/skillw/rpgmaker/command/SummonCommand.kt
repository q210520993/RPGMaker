package com.skillw.rpgmaker.command

import com.skillw.rpgmaker.command.SummonCommand.EntityFactory
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType
import net.minestom.server.command.builder.condition.Conditions
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.utils.location.RelativeVec
import java.util.function.Supplier

@AutoRegistry
object SummonCommand: CommandRegistry, Command("summon") {

    override val key: Command = this

    var entity: ArgumentEntityType
    var pos: Argument<RelativeVec>
    var entityClass:  Argument<EntityClass>

    val entitys = HashSet<Entity>()

    init {
        setCondition(Conditions::playerOnly)

        entity = ArgumentType.EntityType("entity")
        pos = ArgumentType.RelativeVec3("pos").setDefaultValue(Supplier {
            RelativeVec(
                Vec(0.0, 0.0, 0.0),
                RelativeVec.CoordinateType.RELATIVE,
                true, true, true
            )
        })
        entityClass = ArgumentType.Enum("class", EntityClass::class.java)
            .setFormat(ArgumentEnum.Format.LOWER_CASED)
            .setDefaultValue(EntityClass.CREATURE)

        addSyntax({ commandSender: CommandSender, commandContext: CommandContext ->
            this.execute(
                commandSender,
                commandContext
            )
        }, entity, pos, entityClass)

    }

    private fun execute(commandSender: CommandSender, commandContext: CommandContext) {
        val entity = commandContext.get(entityClass).instantiate(
            commandContext.get(
                this.entity
            )
        )
        entity.setInstance(
            (commandSender as Player).instance,
            commandContext.get(pos).fromSender(commandSender)
        )
        entitys.add(entity)
    }

    enum class EntityClass(private val factory: EntityFactory) {
        BASE(EntityFactory { entityType: EntityType -> Entity(entityType) }),
        LIVING(EntityFactory { entityType: EntityType -> entityType.let { net.minestom.server.entity.LivingEntity(it) } }),
        CREATURE(EntityFactory { entityType: EntityType ->
            entityType.let { net.minestom.server.entity.EntityCreature(it) }
        });

        fun instantiate(type: EntityType): Entity {
            return factory.newInstance(type)
        }
    }

    fun interface EntityFactory {
        fun newInstance(type: EntityType): Entity
    }

}