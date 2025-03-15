package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.russellbanks.Database
import com.russellbanks.data.GuildPrefs
import dev.kord.cache.api.data.description
import dev.kord.cache.api.put
import dev.kord.cache.api.query
import dev.kord.cache.map.MapDataCache
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.GuildBehavior
import dev.kord.core.behavior.channel.ChannelBehavior
import extensions.voicestateupdate.Action
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Properties

object Dao {
    private val mutex = Mutex()

    private val driver: SqlDriver = JdbcSqliteDriver(
        url = "jdbc:sqlite:${System.getProperty("user.dir")}/notify.db" +
                "?busy_timeout=5000&journal_mode=WAL",
        properties = Properties().apply {
            put("foreign_keys", "true")
        }
    ).apply {
        if (!getConnection().metaData.getTables(null, null, "guildPrefs", null).next()) {
            Database.Schema.create(this)
        }
    }

    private val database = Database(driver)
    private val queries = database.guildInfoQueries
    private val cache = MapDataCache()
    private val description = description(GuildPrefs::guildId)

    suspend fun setupCache() = mutex.withLock {
        cache.register(description)
    }

    suspend fun get(guild: GuildBehavior): GuildPrefs = get(guild.id)

    private suspend fun get(guildId: Snowflake): GuildPrefs = mutex.withLock {
        val cachedRecord = cache.query<GuildPrefs> {
            GuildPrefs::guildId eq guildId.toString()
        }.singleOrNull()

        cachedRecord ?: queries.getGuild(guildId.toString()).executeAsOne().also {
            cache.put(it)
        }
    }

    suspend fun isNewGuild(guild: GuildBehavior): Boolean = mutex.withLock {
        !queries.isNewGuild(guild.id.toString()).executeAsOne()
    }

    suspend fun createGuild(guild: GuildBehavior) = mutex.withLock {
        queries.createGuild(guild.id.toString())
    }

    suspend fun deleteGuild(guild: GuildBehavior) = mutex.withLock {
        queries.deleteGuild(guild.id.toString())
    }

    suspend fun updateChannel(guild: GuildBehavior, channel: ChannelBehavior?) {
        try {
            mutex.withLock {
                queries.updateChannel(
                    channelId = channel?.id?.toString(),
                    guildId = guild.id.toString()
                )
                cache.query<GuildPrefs> { GuildPrefs::guildId eq guild.id.toString() }
                    .update { it.copy(channelId = channel?.id?.toString()) }
            }
        } catch (e: Exception) {
            println("Error updating channel: ${e.message}")
            throw e
        }
    }

    suspend fun update(guild: GuildBehavior, action: Action, toggle: Boolean) {
        mutex.withLock {
            val guildId = guild.id.toString()
            when (action) {
                Action.JOIN -> queries.updateJoin(join = toggle, guildId)
                Action.SWITCH -> queries.updateSwitch(switch = toggle, guildId)
                Action.LEAVE -> queries.updateLeave(leave = toggle, guildId)
                Action.STREAM -> queries.updateStream(stream = toggle, guildId)
                Action.VIDEO -> queries.updateVideo(video = toggle, guildId)
                else -> throw IllegalArgumentException("Invalid feature: $action")
            }
            updateCache(guild)
        }
    }

    private suspend fun updateCache(guild: GuildBehavior) {
        mutex.withLock {
            val guildId = guild.id.toString()
            val dbRecord = queries.getGuild(guildId).executeAsOne()

            cache.query<GuildPrefs> { GuildPrefs::guildId eq guildId }
                .update { dbRecord }
        }
    }
}