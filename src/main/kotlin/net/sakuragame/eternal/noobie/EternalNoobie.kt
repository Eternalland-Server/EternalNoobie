package net.sakuragame.eternal.noobie

import com.lambdaworks.redis.api.StatefulRedisConnection
import net.luckperms.api.LuckPerms
import net.sakuragame.serversystems.manage.api.redis.RedisManager
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
object EternalNoobie : Plugin() {

    @Config("config.yml", autoReload = true)
    lateinit var conf: Configuration
        private set

    val redisManager: RedisManager by lazy {
        ClientManagerAPI.clientPlugin.redisManager
    }

    val redisConn: StatefulRedisConnection<String, String> by lazy {
        redisManager.standaloneConn.also {
            it.setTimeout(200, TimeUnit.MILLISECONDS)
        }
    }

    val luckPermsAPI by lazy {
        Bukkit.getServicesManager().getRegistration(LuckPerms::class.java).provider!!
    }
}