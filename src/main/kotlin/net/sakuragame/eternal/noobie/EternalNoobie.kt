package net.sakuragame.eternal.noobie

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

@Suppress("SpellCheckingInspection")
object EternalNoobie : Plugin() {

    @Config("config.yml")
    lateinit var conf: Configuration
        private set

    val luckPermsAPI by lazy {
        Bukkit.getServicesManager().getRegistration(LuckPerms::class.java).provider!!
    }
}