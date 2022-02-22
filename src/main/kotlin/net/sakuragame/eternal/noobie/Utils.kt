@file:Suppress("SpellCheckingInspection")

package net.sakuragame.eternal.noobie

import org.bukkit.entity.Player
import pl.betoncraft.betonquest.BetonQuest

fun Player.getNoobiePoints(): Int? {
    return BetonQuest.getInstance().getPlayerData(uniqueId).points.find { it.category == "noobie_quest" }?.count
}

fun Player.addNoobiePoints(int: Int) {
    BetonQuest.getInstance().getPlayerData(uniqueId).modifyPoints("noobie_quest", int)
}