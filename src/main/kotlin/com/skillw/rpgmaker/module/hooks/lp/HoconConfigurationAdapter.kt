package com.skillw.rpgmaker.module.hooks.lp

import me.lucko.luckperms.common.config.generic.adapter.ConfigurateConfigAdapter
import me.lucko.luckperms.common.config.generic.adapter.ConfigurationAdapter
import me.lucko.luckperms.common.plugin.LuckPermsPlugin
import me.lucko.luckperms.minestom.LPMinestomPlugin
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import java.nio.file.Path

class HoconConfigurationAdapter(plugin: LuckPermsPlugin) : ConfigurationAdapter, ConfigurateConfigAdapter(
    plugin,
    (plugin as LPMinestomPlugin).resolveConfig("luckperms.conf")
) {
    override fun createLoader(path: Path?): HoconConfigurationLoader? {
        return path?.let { HoconConfigurationLoader.builder().setPath(it).build() }
    }
}