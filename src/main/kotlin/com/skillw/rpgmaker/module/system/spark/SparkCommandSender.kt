package com.skillw.rpgmaker.module.system.spark

import me.lucko.spark.common.command.sender.AbstractCommandSender
import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.entity.Player
import java.util.*

class SparkCommandSender(delegate: CommandSender) : AbstractCommandSender<CommandSender>(delegate) {

    override fun getName(): String {
        return (delegate as? Player)?.username
            ?: if (delegate is ConsoleSender) {
                "Console"
            } else {
                "unknown:" + delegate.javaClass.simpleName
            }
    }

    override fun getUniqueId(): UUID? {
        if (delegate is Player) {
            return (delegate as Player).uuid
        }
        return null
    }

    override fun sendMessage(p0: Component) {
        delegate.sendMessage(p0)
    }

    override fun hasPermission(permission: String): Boolean {
        return true
    }

}