package net.refractored.commands

import dev.minn.jda.ktx.messages.EmbedBuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.Elo
import net.refractored.database.Database
import net.refractored.extension.EloJDASlashCommandActor
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.jda.annotation.OptionData

class BotInfo {
    @OptIn(ExperimentalStdlibApi::class)
    @Description("Gets the bot's information.")
    @Command("bot info")
    fun botInfoCommand(
        actor: EloJDASlashCommandActor,
        @Optional
        @OptionData(
            value = OptionType.BOOLEAN,
            name = "public",
            description = "Should people see this?",
            required = false,
        ) visible: Boolean = false,
    ) {
        val usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024
        val embed =
            EmbedBuilder {
                title = "Elo"
                description = "Used memory: $usedMemory MB\n" +
                    "Max memory: $maxMemory MB\n" +
                    "Available processors: ${Runtime.getRuntime().availableProcessors()}\n" +
                    "Accounts: ${Database.userDao.countOf()}\n"
                color =
                    Elo.instance.config
                        .node("Discord", "embedColor")
                        .string!!
                        .hexToInt(
                            HexFormat {
                                upperCase = false
                                number.prefix = "#"
                                number.removeLeadingZeros = true
                            },
                        )
                thumbnail =
                    Elo.instance.jda.selfUser.avatarUrl
            }.build()
        actor.slashEvent
            .replyEmbeds(embed)
            .setEphemeral(!visible)
            .queue()
    }
}
