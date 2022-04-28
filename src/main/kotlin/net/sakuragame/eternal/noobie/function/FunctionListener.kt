package net.sakuragame.eternal.noobie.function

import net.luckperms.api.node.types.PermissionNode
import net.sakuragame.eternal.dragoncore.api.event.YamlSendFinishedEvent
import net.sakuragame.eternal.justmessage.api.MessageAPI
import net.sakuragame.eternal.justquest.JustQuest
import net.sakuragame.eternal.justquest.api.JustQuestAPI
import net.sakuragame.eternal.justquest.api.event.QuestAccountLoadEvent
import net.sakuragame.eternal.noobie.EternalNoobie
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
object FunctionListener {

    private const val PERMISSION = "noobie_tutorial"
    private const val QUEST_ID = "foreword"

    @SubscribeEvent
    fun e(e: QuestAccountLoadEvent) {
        submit(async = false, delay = 20L) {
            val player = e.player ?: return@submit
            val account = e.account
            if (player.hasPermission(PERMISSION)) {
                return@submit
            }
            if (account.finished.contains(QUEST_ID) || account.questProgress.keys.contains(QUEST_ID)) {
                return@submit
            }
            JustQuestAPI.allotQuest(player, QUEST_ID)
            player.sendTitle("", "&7&o一百年后...".colored(), 10, 100, 10)
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 99999999, 40, false))
            submit(delay = 40) {
                player.sendTitle("", "&d&o诶诶诶，那个谁，你怎么睡在地上啊。(好听的女声)".colored(), 10, 100, 10)
                MessageAPI.sendActionTip(player, "&6&l➱ &e提示: 去找樱儿.")
                player.removePotionEffect(PotionEffectType.CONFUSION)
                EternalNoobie.luckPermsAPI.userManager.also {
                    val user = it.getUser(player.uniqueId)!!
                    user.data().add(PermissionNode.builder("noobie_tutorial").build())
                    it.saveUser(user)
                }
            }
        }
    }
}