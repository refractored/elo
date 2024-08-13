package net.refractored.commands

import net.refractored.database.Database
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.jda.actor.SlashCommandJDAActor

class BotInfo {
    @Description("Gets the bot's information.")
    @Command("bot info")
    fun botInfoCommand(actor: SlashCommandJDAActor) {
        val usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024
        actor.reply(
            "Used memory: $usedMemory MB\n" +
                "Max memory: $maxMemory MB\n" +
                "Available processors: ${Runtime.getRuntime().availableProcessors()}\n" +
                "Accounts: ${Database.userDao.countOf()}\n",
        )
    }
}
