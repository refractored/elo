package net.refractored.commands

import dev.minn.jda.ktx.messages.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.Elo
import net.refractored.extension.EloJDASlashCommandActor
import net.refractored.user.EloUser
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.jda.annotation.OptionData

class AddElo {
    @OptIn(ExperimentalStdlibApi::class)
    @Description("Adds elo to a user.")
    @Command("elo add")
    fun setEloCommand(
        actor: EloJDASlashCommandActor,
        @OptionData(
            value = OptionType.USER,
            name = "user",
            description = "Who to set the elo for?",
        ) user: User,
        @OptionData(
            value = OptionType.INTEGER,
            name = "value",
            description = "How many points to add?",
        ) points: Int,
        @Optional
        @OptionData(
            value = OptionType.BOOLEAN,
            name = "public",
            description = "Should people see this?",
            required = false,
        ) visible: Boolean = false,
    ) {
        EloUser.addPoints(user, points)
        if (user.isBot) {
            actor.slashEvent
                .reply("Bots can't have elo!")
                .setEphemeral(true)
                .queue()
            return
        }
        val embed =
            EmbedBuilder {
                title = "Elo"
                description = "Added $points points to ${user.asMention}"
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
                    user.avatar?.url
            }.build()
        actor.slashEvent
            .replyEmbeds(embed)
            .setEphemeral(!visible)
            .queue()
    }
}
