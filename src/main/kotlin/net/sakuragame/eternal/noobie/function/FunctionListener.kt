package net.sakuragame.eternal.noobie.function

import net.sakuragame.eternal.dragoncore.api.event.YamlSendFinishedEvent
import net.sakuragame.eternal.justmessage.api.MessageAPI
import net.sakuragame.eternal.kirradungeon.client.compat.StoryDungeonCompat
import net.sakuragame.eternal.kirraparty.bukkit.event.PartyCreateEvent
import net.sakuragame.eternal.noobie.addNoobiePoints
import net.sakuragame.eternal.noobie.getNoobiePoints
import net.sakuragame.kirracoords.KirraCoordsAPI
import net.sakuragame.kirracore.bukkit.KirraCoreBukkitAPI
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import pl.betoncraft.betonquest.BetonQuest
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
object FunctionListener {

    val succCreateTeamMessage = "&6&l➱ &e成功创建队伍, 快找樱儿答复吧!".colored()

    @SubscribeEvent(EventPriority.HIGHEST)
    fun e(e: PlayerJoinEvent) {
        if (!isSpawnServer()) return
        submit(delay = 10L) {
            doJoinTask(e.player)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        if (!isSpawnServer()) return
        val player = e.player
        if (player.getNoobiePoints() == null) {
            player.removePotionEffect(PotionEffectType.BLINDNESS)
            player.removePotionEffect(PotionEffectType.CONFUSION)
        }
    }

    @SubscribeEvent
    fun e(e: PartyCreateEvent) {
        if (!isSpawnServer()) return
        val player = e.player
        val points = player.getNoobiePoints() ?: return
        if (points == 2) {
            player.sendMessage(succCreateTeamMessage)
            player.addNoobiePoints(1)
            BetonQuest.getInstance().getPlayerData(player.uniqueId).journal.update()
        }
    }

    @SubscribeEvent
    fun e(e: YamlSendFinishedEvent) {
        if (!isSpawnServer()) return
        submit(delay = 3L) {
            val player = e.player
            if (player.hasPermission("noobie_tutorial")) {
                return@submit
            }
            if (player.getNoobiePoints() == null) {
                return@submit
            }
            val noobiePoints = player.getNoobiePoints()!!
            if (noobiePoints >= 3) {
                return@submit
            }
            player.sendTitle("", "&7&o一百年后...".colored(), 10, 100, 10)
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 99999999, 40, false))
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 99999999, 40, false))
            submit(delay = 40) {
                player.sendTitle("", "&d&o诶诶诶，那个谁，你怎么睡在地上啊。(好听的女声)".colored(), 10, 100, 10)
                MessageAPI.sendActionTip(player, "&6&l➱ &e提示: 去找樱儿.")
                player.removePotionEffect(PotionEffectType.BLINDNESS)
                player.removePotionEffect(PotionEffectType.CONFUSION)
            }
        }
    }

    private fun doJoinTask(player: Player) {
        if (player.hasPermission("noobie_tutorial")) {
            return
        }
        if (player.getNoobiePoints() == null) {
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 99999999, 100))
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 99999999, 100))
            val isSucc = StoryDungeonCompat.join(player)
            if (!isSucc) {
                KirraCoreBukkitAPI.teleportPlayerToAnotherServer("rpg-login-1", player)
            }
            return
        }
        KirraCoordsAPI.tpCoord(player, "noobie_tutorial_spawn")
    }
    private fun isSpawnServer() = ClientManagerAPI.getServerID().startsWith("rpg-spawn")
}