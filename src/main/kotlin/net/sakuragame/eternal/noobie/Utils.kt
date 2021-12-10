@file:Suppress("SpellCheckingInspection")

package net.sakuragame.eternal.noobie

import net.luckperms.api.model.user.User
import org.bukkit.entity.Player
import pl.betoncraft.betonquest.BetonQuest

fun Player.getNoobiePoints(): Int? {
    return BetonQuest.getInstance().getPlayerData(uniqueId).points.firstOrNull { it.category == "noobie_quest" }?.count
}

fun Player.addNoobiePoints(int: Int) {
    BetonQuest.getInstance().getPlayerData(uniqueId).modifyPoints("noobie_quest", int)
}

fun User.hasPermission(permission: String) = cachedData.permissionData.checkPermission(permission).asBoolean()