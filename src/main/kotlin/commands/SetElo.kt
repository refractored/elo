package net.refractored.commands

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.user.EloUser
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.jda.JDAActor
import revxrsal.commands.jda.annotation.OptionData

class SetElo {
    @Description("Sets the user's elo.")
    @Command("elo set")
    fun setEloCommand(
        actor: JDAActor,
        @OptionData(
            value = OptionType.USER,
            name = "user",
            description = "Who to set the elo for?",
        ) user: User,
        @OptionData(
            value = OptionType.INTEGER,
            name = "value",
            description = "How many points to set?",
        ) points: Int,
    ) {
        EloUser.setPoints(user, points)
        actor.reply("Set $points points for ${user.asTag}")
    }
}
