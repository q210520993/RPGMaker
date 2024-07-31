package com.skillw.rpgmaker.system.spark

import com.skillw.rpgmaker.RPGMaker
import me.lucko.spark.common.SparkPlatform
import me.lucko.spark.common.SparkPlugin
import me.lucko.spark.common.command.sender.CommandSender
import me.lucko.spark.common.monitor.ping.PlayerPingProvider
import me.lucko.spark.common.platform.PlatformInfo
import me.lucko.spark.common.sampler.source.ClassSourceLookup
import me.lucko.spark.common.tick.TickHook
import me.lucko.spark.common.tick.TickReporter
import net.minestom.server.MinecraftServer
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.logging.Level
import java.util.stream.Stream

object SparkMinestom : SparkPlugin {

    val logger = LoggerFactory.getLogger(SparkMinestom::class.java)!!

    lateinit var SparkPlatform: SparkPlatform
    lateinit var command: SparkCommand


    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun getPluginDirectory(): Path {
        return Path.of("spark")
    }

    override fun getCommandName(): String {
        return "spark"
    }

    override fun getCommandSenders(): Stream<out CommandSender> {
        return Stream.concat(
            MinecraftServer.getConnectionManager().onlinePlayers.stream(),
            Stream.of(MinecraftServer.getCommandManager().consoleSender)
        )
            .map { SparkCommandSender(it) };
    }

    override fun executeAsync(p0: Runnable) {
        CompletableFuture.runAsync { p0.run() }
    }

    override fun getPlatformInfo(): PlatformInfo {
        return Platform
    }

    override fun log(level: Level, msg: String?) {
        if (level === Level.INFO) {
            logger.info(msg)
        } else if (level === Level.WARNING) {
            logger.warn(msg)
        } else if (level === Level.SEVERE) {
            logger.error(msg)
        } else {
            throw IllegalArgumentException(level.name)
        }
    }

    override fun createTickReporter(): TickReporter {
        return TickReporter
    }

    override fun createClassSourceLookup(): ClassSourceLookup {
        return ClassLookup(Thread.currentThread().contextClassLoader)
    }

    override fun createTickHook(): TickHook {
        return Tick
    }

    override fun createPlayerPingProvider(): PlayerPingProvider {
        return PlayerPing
    }

    fun hook(rpgMaker: RPGMaker) {
        val platform = SparkPlatform(this)
        SparkPlatform = platform
        SparkPlatform.enable()
        command = SparkCommand(SparkPlatform)
        MinecraftServer.getCommandManager().register(command)
        MinecraftServer.getSchedulerManager().buildShutdownTask {
            SparkPlatform.disable()
            MinecraftServer.getCommandManager().unregister(command)
        }
    }

}