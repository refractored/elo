package net.refractored.extension

import revxrsal.commands.jda.actor.SlashCommandJDAActor
import java.text.MessageFormat

abstract class EloJDASlashCommandActor : SlashCommandJDAActor {
    fun replyEphemeral(message: String) {
        slashEvent.reply(message).setEphemeral(true).queue()
    }

    override fun replyLocalized(
        key: String,
        vararg args: Any?,
    ) {
        val message = MessageFormat.format(translator[key, locale], *args)
        replyEphemeral(message)
    }
}
