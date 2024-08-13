package net.refractored

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.refractored.commands.*
import net.refractored.database.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import revxrsal.commands.jda.JDACommandHandler
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Elo {
    val logger: Logger = LoggerFactory.getLogger(Logger::class.java)

    private var configLoader: YamlConfigurationLoader

    val config: CommentedConfigurationNode

    val dataFolder = File(Paths.get("").toAbsolutePath().toString())

    val commandHandler: JDACommandHandler

    val jda: JDA

    init {
        instance = this
        if (!File(dataFolder, "elo.yml").exists()) {
            javaClass.getResourceAsStream("/elo.yml")?.let {
                Files.copy(
                    it,
                    Paths.get("elo.yml"),
                )
            }
        }
        configLoader =
            YamlConfigurationLoader
                .builder()
                .path(Paths.get("elo.yml"))
                .build()
        config = configLoader.load()
        Database.init()
        jda =
            JDABuilder
                .createDefault(
                    config.node("Discord", "token").string,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MEMBERS,
                ).build()
        commandHandler = JDACommandHandler.create(instance.jda)
        commandHandler.unregisterAllCommands()
        commandHandler.register(SetElo())
        commandHandler.register(GetElo())
        commandHandler.register(AddElo())
        commandHandler.register(RemoveElo())
        commandHandler.register(BotInfo())
        commandHandler.registerSlashCommands()
    }

    companion object {
        @JvmStatic
        lateinit var instance: Elo
            private set

        @JvmStatic
        fun main(args: Array<String>) {
            instance = Elo()
        }
    }
}
