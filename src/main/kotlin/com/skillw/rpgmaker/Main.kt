package com.skillw.rpgmaker

import com.skillw.rpgmaker.system.spark.SparkMinestom
import me.lucko.spark.api.SparkProvider
import me.lucko.spark.api.statistic.StatisticWindow
import net.minestom.server.MinecraftServer


fun main() {

    val minecraft = MinecraftServer.init()
    val rpgMaker = RPGMaker(minecraft)
    RPGMakerInstance.rpgMaker = rpgMaker
    rpgMaker.init()
    SparkMinestom.hook(rpgMaker)
    val tps = SparkProvider.get().tps()
    val tpsLast10Secs: Double? = tps?.poll(StatisticWindow.TicksPerSecond.SECONDS_10)
    val tpsLast5Mins: Double? = tps?.poll(StatisticWindow.TicksPerSecond.MINUTES_5)
    println(tpsLast5Mins)
    println(tpsLast10Secs)

}