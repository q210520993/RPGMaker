package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.RPGMaker
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.module.player.RPGPlayer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.permission.Permission

@AutoRegistry
object TestPermission: CommandRegistry, Command("testlp") {
    override fun getKey(): Command {
        return  this
    }
    init {
        val arg = ArgumentType.String("type")
        addSyntax ({ sender, context ->
            val permission = Permission("test.zzxc")
            if (context.get(arg) == "1") {
                (sender as RPGPlayer).addPermission(permission)
                sender.sendMessage("添加成功$permission")
            }
            (sender as RPGPlayer).sendMessage("玩家有${(sender as RPGPlayer).hasPermission("test.zzxc")}")
            }
        , arg)
    }
}