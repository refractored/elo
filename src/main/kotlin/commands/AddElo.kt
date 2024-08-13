package net.refractored.commands

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.user.EloUser
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.jda.actor.SlashCommandJDAActor
import revxrsal.commands.jda.annotation.OptionData

class AddElo {
    @Description("Adds elo to a user.")
    @Command("elo add")
    fun setEloCommand(
        actor: SlashCommandJDAActor,
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
    ) {
        EloUser.addPoints(user, points)
        actor.reply("")
        actor.slashEvent
            .reply("Set $points points for ${user.asTag}")
            .setEphemeral(true)
            .queue()
    }
}
