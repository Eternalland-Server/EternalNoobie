package net.sakuragame.eternal.noobie

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin
import java.io.File

@Suppress("SpellCheckingInspection", "MemberVisibilityCanBePrivate")
object EternalNoobie : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    val dataFile by lazy {
        val file = File(plugin.dataFolder, "data.yml")
        if (!file.exists()) {
            file.createNewFile()
        }
        Configuration.loadFromFile(file)
    }

    val luckPermsAPI by lazy {
        Bukkit.getServicesManager().getRegistration(LuckPerms::class.java).provider!!
    }
}