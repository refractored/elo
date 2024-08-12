package net.refractored.commands

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.refractored.user.EloUser
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.jda.JDAActor
import revxrsal.commands.jda.annotation.OptionData

class GetElo {
    @Description("Sets the user's elo.")
    @Command("elo get")
    fun getEloCommand(
        actor: JDAActor,
        @OptionData(
            value = OptionType.USER,
            name = "user",
            description = "Who to get the elo for?",
        ) user: User,
    ) {
        actor.reply("${user.asTag} has ${EloUser.getPoints(user)} points")
    }
}
