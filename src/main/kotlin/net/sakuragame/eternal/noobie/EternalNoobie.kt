package net.sakuragame.eternal.noobie

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

@Suppress("SpellCheckingInspection", "MemberVisibilityCanBePrivate")
object EternalNoobie : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    val luckPermsAPI by lazy {
        Bukkit.getServicesManager().getRegistration(LuckPerms::class.java).provider!!
    }
}