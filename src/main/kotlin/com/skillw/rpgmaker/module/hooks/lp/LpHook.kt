package com.skillw.rpgmaker.module.hooks.lp

import com.skillw.rpgmaker.RPGMaker
import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.handlers.annotations.CoreHook
import com.skillw.rpgmaker.core.handlers.hook.ICoreHook
import me.lucko.luckperms.common.config.generic.adapter.EnvironmentVariableConfigAdapter
import me.lucko.luckperms.common.config.generic.adapter.MultiConfigurationAdapter
import me.lucko.luckperms.minestom.CommandRegistry
import me.lucko.luckperms.minestom.LPMinestomPlugin
import me.lucko.luckperms.minestom.LuckPermsMinestom
import me.lucko.luckperms.minestom.context.ContextProvider
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


@CoreHook
object LpHook: ICoreHook {
    override fun hook(rpgMaker: RPGMaker) {
        val path = Path.of("luckperms")
        val luckPerms = LuckPermsMinestom.builder(path)
            .commandRegistry(CommandRegistry.minestom())
            .contextProvider(object : ContextProvider {
                override fun key(): String {
                    return "dummy"
                }

                override fun query(subject: Player): Optional<String> {
                    return Optional.of("true")
                }
            }) // provide additional custom contexts
            .configurationAdapter { plugin: LPMinestomPlugin? ->
                MultiConfigurationAdapter(
                    plugin,  // define the configuration
                    EnvironmentVariableConfigAdapter(plugin),  // use MultiConfigurationAdapter to load from multiple sources, in order
                    plugin?.let { HoconConfigurationAdapter(it) }
                )
            }.permissionSuggestions(
                "test.permission",
                "test.other"
            ) // add permission suggestions for commands and the web editor
            .dependencyManager(true) // automatically download and classload dependencies
            .enable()
        RPGMakerInstance.luckPerms = luckPerms
        // register shutdown hook to delete the temp directory
        MinecraftServer.getSchedulerManager().buildShutdownTask {
            try {
                LuckPermsMinestom.disable()
                Files.deleteIfExists(path)
            } catch (ignored: IOException) {
                // oh well...
            }
        }
    }
}