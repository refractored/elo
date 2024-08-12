package net.refractored

import net.dv8tion.jda.api.JDABuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.nio.file.Paths

class Elo {
    val logger: Logger = LoggerFactory.getLogger(Logger::class.java)

    val configLoader: YamlConfigurationLoader =
        YamlConfigurationLoader
            .builder()
            .path(Paths.get("elo.yml"))
            .build()
    val configRoot = configLoader.load()

    val jda = JDABuilder.createLight(configRoot.node("Discord", "token").string).build()

    init {
    }

    companion object {
        @JvmStatic
        var instance: Elo? = null
            private set

        @JvmStatic
        fun main(args: Array<String>) {
            instance = Elo()
        }
    }
}
