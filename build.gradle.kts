plugins {
    kotlin("jvm") version "2.0.0"
//    application
    id("io.ktor.plugin") version "2.3.12"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.refractored"
version = "1.0-SNAPSHOT"

val jdaVersion = "5.0.2"
val lampVersion = "3.2.1"
val ormliteVersion = "6.1"
val configurateVersion = "4.1.2"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

application {
    mainClass.set("$group.Elo")
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("club.minnced:jda-ktx:0.12.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.spongepowered:configurate-yaml:$configurateVersion")
    implementation("org.spongepowered:configurate-extra-kotlin:$configurateVersion")
    implementation("com.github.Revxrsal.Lamp:common:$lampVersion")
    implementation("com.github.Revxrsal.Lamp:jda:$lampVersion")
    implementation("com.j256.ormlite:ormlite-core:$ormliteVersion")
    implementation("com.j256.ormlite:ormlite-jdbc:$ormliteVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true

    // Set this to the version of java you want to use,
    // the minimum required for JDA is 1.8
    sourceCompatibility = "21"
}
