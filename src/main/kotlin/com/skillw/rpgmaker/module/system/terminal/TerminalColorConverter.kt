package com.skillw.rpgmaker.module.system.terminal

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.regex.MatchResult
import java.util.regex.Pattern

internal object TerminalColorConverter {
    private val SUPPORT_HEX_COLOR: Boolean = true
    private val SUPPORT_COLOR: Boolean = true

    private const val RGB_ANSI = "\u001B[38;2;%d;%d;%dm"
    private const val ANSI_RESET = "\u001B[m"
    private const val LOOKUP = "0123456789abcdefklmnor"
    private val ANSI_CODES = arrayOf(
        getAnsiColor(NamedTextColor.BLACK, "\u001B[0;30m"),
        getAnsiColor(NamedTextColor.DARK_BLUE, "\u001B[0;34m"),
        getAnsiColor(NamedTextColor.DARK_GREEN, "\u001B[0;32m"),
        getAnsiColor(NamedTextColor.DARK_AQUA, "\u001B[0;36m"),
        getAnsiColor(NamedTextColor.DARK_RED, "\u001B[0;31m"),
        getAnsiColor(NamedTextColor.DARK_PURPLE, "\u001B[0;35m"),
        getAnsiColor(NamedTextColor.GOLD, "\u001B[0;33m"),
        getAnsiColor(NamedTextColor.GRAY, "\u001B[0;37m"),
        getAnsiColor(NamedTextColor.DARK_GRAY, "\u001B[0;30;1m"),
        getAnsiColor(NamedTextColor.BLUE, "\u001B[0;34;1m"),
        getAnsiColor(NamedTextColor.GREEN, "\u001B[0;32;1m"),
        getAnsiColor(NamedTextColor.AQUA, "\u001B[0;36;1m"),
        getAnsiColor(NamedTextColor.RED, "\u001B[0;31;1m"),
        getAnsiColor(NamedTextColor.LIGHT_PURPLE, "\u001B[0;35;1m"),
        getAnsiColor(NamedTextColor.YELLOW, "\u001B[0;33;1m"),
        getAnsiColor(NamedTextColor.WHITE, "\u001B[0;37;1m"),
        "\u001B[5m",
        "\u001B[1m",
        "\u001B[9m",
        "\u001B[4m",
        "\u001B[3m",
        ANSI_RESET,
    )
    private val RGB_PATTERN: Pattern =
        Pattern.compile(LegacyComponentSerializer.SECTION_CHAR.toString() + "#([\\da-fA-F]{6})")
    private val NAMED_PATTERN: Pattern =
        Pattern.compile(LegacyComponentSerializer.SECTION_CHAR.toString() + "([\\da-fk-orA-FK-OR])")

    private fun getAnsiColor(color: NamedTextColor, fallback: String): String {
        return getAnsiColorFromHexColor(color.value(), fallback)
    }

    private fun getAnsiColorFromHexColor(color: Int, fallback: String): String {
        return if (SUPPORT_HEX_COLOR) String.format(
            RGB_ANSI,
            (color shr 16) and 0xFF,
            (color shr 8) and 0xFF, color and 0xFF
        ) else fallback
    }

    private fun getAnsiColorFromHexColor(color: Int): String {
        return getAnsiColorFromHexColor(color, "")
    }

    fun format(string: String): String {
        var string = string
        if (string.indexOf(LegacyComponentSerializer.SECTION_CHAR) == -1) {
            return string
        }

        string = RGB_PATTERN.matcher(string).replaceAll { match: MatchResult ->
            if (SUPPORT_COLOR) {
                val hex = match.group(1)
                return@replaceAll getAnsiColorFromHexColor(hex.toInt(16))
            } else {
                return@replaceAll ""
            }
        }

        val matcher = NAMED_PATTERN.matcher(string)
        val builder = StringBuilder()
        while (matcher.find()) {
            val format = LOOKUP.indexOf(matcher.group()[1].lowercaseChar())
            if (format != -1) {
                matcher.appendReplacement(builder, if (SUPPORT_COLOR) ANSI_CODES[format] else "")
            } else {
                matcher.appendReplacement(builder, matcher.group())
            }
        }
        matcher.appendTail(builder)

        if (SUPPORT_COLOR) {
            builder.append(ANSI_RESET)
        }
        return builder.toString()
    }
}