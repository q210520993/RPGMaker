package com.skillw.rpgmaker.module.system.terminal

import org.fusesource.jansi.AnsiConsole
import org.tinylog.core.LogEntry
import org.tinylog.writers.AbstractFormatPatternWriter

class EasyConsoleWriter(properties: Map<String?, String?>?) : AbstractFormatPatternWriter(properties) {
    @Throws(Exception::class)
    override fun write(logEntry: LogEntry) {
        val rendered = render(logEntry)
        val formatted: String = TerminalColorConverter.format(rendered)
        if (EasyTerminal.reader != null) {
            EasyTerminal.reader?.printAbove(formatted)
        } else {
            AnsiConsole.out().print(formatted)
        }
    }

    override fun flush() {

    }

    override fun close() {

    }
}