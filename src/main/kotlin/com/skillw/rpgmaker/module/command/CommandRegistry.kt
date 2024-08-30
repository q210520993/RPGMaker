package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.core.map.component.Registrable
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command

/*
* @author Clok
* @date 8.16 失恋愤怒版
* */
interface CommandRegistry: Registrable<Command> {

    override fun register() {
        MinecraftServer.getCommandManager().register(getKey())
    }
}