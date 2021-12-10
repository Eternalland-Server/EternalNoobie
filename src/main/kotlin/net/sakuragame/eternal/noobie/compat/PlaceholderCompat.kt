package net.sakuragame.eternal.noobie.compat

import net.sakuragame.eternal.noobie.getNoobiePoints
import org.bukkit.entity.Player
import taboolib.module.chat.colored
import taboolib.platform.compat.PlaceholderExpansion

@Suppress("SpellCheckingInspection")
object PlaceholderCompat : PlaceholderExpansion {

    override val identifier = "EternalNoobie".lowercase()

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        if (player == null) {
            return "__"
        }
        when (player.getNoobiePoints()) {
            1 -> return "&f&l去找樱儿.".colored()
            2 -> return "&f&l用 I 创建队伍.".colored()
            3 -> return "&f&l通关新手平原副本.".colored()
            4 -> return "&f&l去找樱儿领取奖励.".colored()
        }
        return "__"
    }
}