package net.refractored.commands

import dev.minn.jda.ktx.messages.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.Elo
import net.refractored.user.EloUser
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.jda.actor.SlashCommandJDAActor
import revxrsal.commands.jda.annotation.OptionData

class GetElo {
    @OptIn(ExperimentalStdlibApi::class)
    @Description("Sets the user's elo.")
    @Command("elo get")
    fun getEloCommand(
        actor: SlashCommandJDAActor,
        @Optional
        @OptionData(
            value = OptionType.USER,
            name = "user",
            description = "Who to get the elo for?",
            required = false,
        ) user: User = actor.slashEvent.user,
        @Optional
        @OptionData(
            value = OptionType.BOOLEAN,
            name = "public",
            description = "Should people see this?",
            required = false,
        ) visible: Boolean = true,
    ) {
        val embed =
            EmbedBuilder {
                title = "Elo"
                description = "${user.asMention} has ${EloUser.getPoints(user)} points."
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
                    Elo.instance.jda.selfUser.avatar!!
                        .url
            }.build()
        actor.slashEvent
            .replyEmbeds(embed)
            .setEphemeral(!visible)
            .queue()
    }
}
