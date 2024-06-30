package com.skillw.rpgmaker.util


import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * @className Runnable
 *
 * @author Glom
 * @date 2023/1/7 22:18 Copyright 2024 Glom.
 */


fun <T> safe(run: () -> T): T? {
    return runCatching { run() }.run {
        if (isSuccess) getOrNull()
        else {
            exceptionOrNull()?.printStackTrace()
            null
        }
    }
}


fun <T> silent(run: () -> T): T? {
    return runCatching { run() }.getOrNull()
}


fun time(times: Int = 1, exec: () -> Unit): Duration {
    val mark = TimeSource.Monotonic.markNow()
    repeat(times) {
        exec()
    }
    return mark.elapsedNow()
}

fun <R> time(exec: () -> R): Pair<R, Duration> {
    val mark = TimeSource.Monotonic.markNow()
    return exec() to mark.elapsedNow()
}

fun <R> timeSafe(exec: () -> R): Pair<R?, Duration> {
    val mark = TimeSource.Monotonic.markNow()
    return safe { exec() } to mark.elapsedNow()
}

fun <R> timeSafeSilent(exec: () -> R): Pair<R?, Duration> {
    val mark = TimeSource.Monotonic.markNow()
    return silent { exec() } to mark.elapsedNow()
}
