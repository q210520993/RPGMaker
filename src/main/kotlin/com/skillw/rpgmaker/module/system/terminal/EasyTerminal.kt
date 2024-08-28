package com.skillw.rpgmaker.module.system.terminal

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.listener.TabCompleteListener
import org.jline.reader.*
import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import java.io.IOException
import java.util.*
import kotlin.concurrent.Volatile
import kotlin.system.exitProcess

object EasyTerminal {

    private const val PROMPT = "> "

    @Volatile
    private var terminal: Terminal? = null

    @Volatile
    var reader: LineReader? = null

    @Volatile
    private var running = false

    fun start() {
        val thread = Thread(null, Runnable {
            try {
                terminal = TerminalBuilder.terminal()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            reader = LineReaderBuilder.builder()
                .completer(EasyCompleter())
                .terminal(terminal)
                .build()
            running = true
            while (running) {
                var command: String?
                try {
                    command = (reader as LineReader).readLine(PROMPT)
                    val commandManager = MinecraftServer.getCommandManager()
                    commandManager.execute(commandManager.consoleSender, command)
                } catch (e: UserInterruptException) {
                    exitProcess(0)
                } catch (e: EndOfFileException) {
                    return@Runnable
                }
            }
        }, "Jline")
        thread.isDaemon = true
        thread.start()
    }

    fun stop() {
        running = false
        if (terminal != null) {
            try {
                terminal!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            reader = null
        }
    }

    private class EasyCompleter : Completer {
        override fun complete(reader: LineReader, line: ParsedLine, candidates: MutableList<Candidate>) {
            val commandManager = MinecraftServer.getCommandManager()
            val consoleSender = commandManager.consoleSender
            if (line.wordIndex() == 0) {
                val commandString = line.word().lowercase(Locale.getDefault())
                candidates.addAll(
                    commandManager.dispatcher.commands.stream()
                        .map { obj: Command -> obj.name }
                        .filter { name: String ->
                            commandString.isBlank() || name.lowercase(
                                Locale.getDefault()
                            ).startsWith(commandString)
                        }
                        .map { value: String? ->
                            Candidate(
                                value
                            )
                        }
                        .toList()
                )
            } else {
                val text = line.line()
                val suggestion = TabCompleteListener.getSuggestion(consoleSender, text)
                suggestion?.entries?.stream()?.map { obj: SuggestionEntry -> obj.entry }
                    ?.map { value: String? ->
                        Candidate(
                            value
                        )
                    }?.forEach { e: Candidate ->
                        candidates.add(
                            e
                        )
                    }
            }
        }
    }
}