package net.sakuragame.eternal.noobie.function

import ink.ptms.zaphkiel.ZaphkielAPI
import net.luckperms.api.node.types.PermissionNode
import net.sakuragame.eternal.gemseconomy.api.GemsEconomyAPI
import net.sakuragame.eternal.gemseconomy.currency.EternalCurrency
import net.sakuragame.eternal.noobie.EternalNoobie
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.command.command
import taboolib.platform.util.giveItem

@Suppress("SpellCheckingInspection")
object FunctionNoobieCmd {

    @Awake(LifeCycle.ACTIVE)
    fun init() {
        command("get-noobie-rewards") {
            execute<Player> { player, _, _ ->
                GemsEconomyAPI.deposit(player.uniqueId, 5000000.0, EternalCurrency.Money)
                GemsEconomyAPI.deposit(player.uniqueId, 100000.0, EternalCurrency.Coins)
                EternalNoobie.luckPermsAPI.userManager.also {
                    val user = it.getUser(player.uniqueId)!!
                    user.data().add(PermissionNode.builder("noobie_tutorial").build())
                    it.saveUser(user)
                }
                EternalNoobie.conf.getStringList("reward-list").forEach {
                    val item = ZaphkielAPI.getItem(it, player)!!.rebuildToItemStack(player)
                    player.giveItem(item)
                }
            }
        }
    }
}