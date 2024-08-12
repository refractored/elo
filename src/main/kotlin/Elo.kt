package net.refractored

import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.nio.file.Paths

class Elo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val configLoader: YamlConfigurationLoader =
                YamlConfigurationLoader
                    .builder()
                    .path(Paths.get("elo.yml"))
                    .build()
            val configRoot = configLoader.load()
            println(configRoot.node("hello").string)
            println("Hello World!")
        }
    }
}
