package net.sakuragame.eternal.noobie.function

import net.sakuragame.eternal.justmessage.api.MessageAPI
import net.sakuragame.eternal.justquest.api.JustQuestAPI
import net.sakuragame.eternal.justquest.api.event.QuestAccountLoadEvent
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
object FunctionListener {

    private const val QUEST_ID = "foreword"

    @SubscribeEvent
    fun e(e: QuestAccountLoadEvent) {
        submit(async = false, delay = 20L) {
            val player = e.player ?: return@submit
            val account = e.account ?: return@submit
            if (KirraCoreBukkitAPI.isAdminPlayer(player)) {
                return@submit
            }
            if (account.finished.contains(QUEST_ID) || account.progresses.keys.contains(QUEST_ID)) {
                return@submit
            }
            JustQuestAPI.allotQuest(player, QUEST_ID)
            player.sendTitle("", "&7&o一百年后...".colored(), 10, 100, 10)
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 999999, 999999, false))
            submit(async = false, delay = 105) {
                player.sendTitle("", "&d&o诶诶诶，那个谁，你怎么睡在地上啊。(好听的女声)".colored(), 10, 100, 10)
                MessageAPI.sendActionTip(player, "&6&l➱ &e提示: 去找樱儿.")
                player.removePotionEffect(PotionEffectType.BLINDNESS)
            }
        }
    }
}