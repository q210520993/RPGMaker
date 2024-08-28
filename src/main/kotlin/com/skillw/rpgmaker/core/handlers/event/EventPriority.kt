package com.skillw.rpgmaker.core.handlers.event

import com.skillw.rpgmaker.core.Priority

enum class EventPriority(val priority: Int): Priority<Int> {

    LOWEST(-24),
    LOW(-12),
    NORMAL(0),
    HIGH(12),
    HIGHEST(24),
    MONITOR(48),
    SERVER(6);

    override fun getPriority(): Int = priority

}