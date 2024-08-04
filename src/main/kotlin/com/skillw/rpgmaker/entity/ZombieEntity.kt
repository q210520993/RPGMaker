package com.skillw.rpgmaker.entity

import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.ai.EntityAIGroupBuilder
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal

class ZombieEntity: EntityCreature(EntityType.ZOMBIE) {
    init {
        val aiGroup = EntityAIGroupBuilder()
            .addGoalSelector(RandomLookAroundGoal(this, 20))
            .build()
        addAIGroup(aiGroup)
    }
}