package com.skillw.rpgmaker.core.handlers.event

enum class EventPriority(val priority: Int) {

    LOWEST(-24),
    LOW(-12),
    NORMAL(0),
    HIGH(12),
    HIGHEST(24),
    MONITOR(48)

}