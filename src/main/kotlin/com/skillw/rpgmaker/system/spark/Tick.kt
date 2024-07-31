package com.skillw.rpgmaker.system.spark

import me.lucko.spark.common.tick.AbstractTickHook
import net.minestom.server.MinecraftServer
import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule

object Tick: AbstractTickHook() {
    private lateinit var task: Task

    override fun start() {
        task = MinecraftServer.getSchedulerManager().
        buildTask(this::onTick).
        delay(TaskSchedule.tick(1)).
        repeat(TaskSchedule.tick(1)).
        schedule()
    }

    override fun close() {
        if (::task.isInitialized) {
            task.cancel()
        }
    }

}