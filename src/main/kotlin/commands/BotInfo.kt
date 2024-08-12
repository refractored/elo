package net.refractored.commands

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.jda.JDAActor
import revxrsal.commands.jda.annotation.OptionData

class BotInfo {
    @Description("Gets the bot's information.")
    @Command("bot info")
    fun botInfoCommand(
        actor: JDAActor,
        @OptionData(
            value = OptionType.USER,
            name = "user",
            description = "Who to get the elo for?",
        ) user: User,
    ) {
        val usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024
        actor.reply("Used memory: $usedMemory MB\nMax memory: $maxMemory MB")
    }
}
