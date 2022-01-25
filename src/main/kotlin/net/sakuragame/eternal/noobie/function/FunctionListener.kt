package net.sakuragame.eternal.noobie.function

import ink.ptms.zaphkiel.ZaphkielAPI
import net.sakuragame.eternal.dragoncore.api.event.YamlSendFinishedEvent
import net.sakuragame.eternal.justmessage.api.MessageAPI
import net.sakuragame.eternal.kirraparty.bukkit.event.PartyCreateEvent
import net.sakuragame.eternal.noobie.addNoobiePoints
import net.sakuragame.eternal.noobie.getNoobiePoints
import net.sakuragame.kirracoords.KirraCoordsAPI
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import pl.betoncraft.betonquest.BetonQuest
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.platform.util.giveItem

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
object FunctionListener {

    val succCreateTeamMessage = "&6&l➱ &e成功创建队伍, 快找樱儿答复吧!".colored()

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        val player = e.player
        if (player.getNoobiePoints() == null) {
            player.removePotionEffect(PotionEffectType.BLINDNESS)
            player.removePotionEffect(PotionEffectType.CONFUSION)
        }
    }

    @SubscribeEvent
    fun e(e: PartyCreateEvent) {
        val player = e.player
        val points = player.getNoobiePoints() ?: return
        if (points == 2) {
            player.sendMessage(succCreateTeamMessage)
            player.addNoobiePoints(1)
            BetonQuest.getInstance().getPlayerData(player.uniqueId).journal.update()
            player.giveItem(ZaphkielAPI.getItem("crabstick", player)!!.rebuildToItemStack(player))
        }
    }

    @SubscribeEvent
    fun e(e: YamlSendFinishedEvent) {
        submit(async = false, delay = 20L) {
            val player = e.player
            if (!player.isOnline) return@submit
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
            KirraCoordsAPI.tpCoord(player, "noobie_tutorial_spawn")
            player.sendTitle("", "&7&o一百年后...".colored(), 10, 100, 10)
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 99999999, 40, false))
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 99999999, 40, false))
            submit(delay = 40) {
                player.sendTitle("", "&d&o诶诶诶，那个谁，你怎么睡在地上啊。(好听的女声)".colored(), 10, 100, 10)
                MessageAPI.sendActionTip(player, "&6&l➱ &e提示: 去找樱儿.")
                player.removePotionEffect(PotionEffectType.BLINDNESS)
                player.removePotionEffect(PotionEffectType.CONFUSION)
                BetonQuest.getInstance().getPlayerData(player.uniqueId).journal.addPointer("noobie_quest_journal_started")
                BetonQuest.getInstance().getPlayerData(player.uniqueId).journal.update()
            }
        }
    }
}