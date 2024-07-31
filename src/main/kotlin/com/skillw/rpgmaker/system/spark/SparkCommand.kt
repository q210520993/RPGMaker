package com.skillw.rpgmaker.system.spark

import me.lucko.spark.common.SparkPlatform
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.suggestion.Suggestion
import net.minestom.server.command.builder.suggestion.SuggestionCallback
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import java.util.*

class SparkCommand(private val platform: SparkPlatform) : Command("spark"),
    CommandExecutor, SuggestionCallback {

    init {
        val arrayArgument = ArgumentType.StringArray("args")
        arrayArgument.setSuggestionCallback(this)

        addSyntax(this, arrayArgument)
        defaultExecutor =
            CommandExecutor { sender: CommandSender, context: CommandContext ->
                platform.executeCommand(
                    SparkCommandSender(sender),
                    arrayOfNulls<String>(0)
                )
            }
    }

    // execute
    override fun apply(sender: CommandSender, context: CommandContext) {
        val args = processArgs(context, false) ?: return

        platform.executeCommand(SparkCommandSender(sender), args)
    }

    // tab complete
    override fun apply(sender: CommandSender, context: CommandContext, suggestion: Suggestion) {
        val args = processArgs(context, true) ?: return

        val suggestionEntries: Iterable<String> =
            platform.tabCompleteCommand(SparkCommandSender(sender), args)
        for (suggestionEntry in suggestionEntries) {
            suggestion.addEntry(SuggestionEntry(suggestionEntry))
        }
    }

    companion object {
        private fun processArgs(context: CommandContext, tabComplete: Boolean): Array<String>? {
            val split = context.input.split(" ".toRegex(), if (tabComplete) Int.MAX_VALUE else 0).toTypedArray()

            if (split.isEmpty() || split[0] != "/spark" && split[0] != "spark") {
                return null
            }

            return Arrays.copyOfRange(split, 1, split.size)
        }
    }

}