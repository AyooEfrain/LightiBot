package extensions.joke

import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.checks.isNotBot
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.modules.unsafe.annotations.UnsafeAPI
import com.kotlindiscord.kord.extensions.modules.unsafe.extensions.unsafeSlashCommand
import com.kotlindiscord.kord.extensions.modules.unsafe.types.InitialSlashCommandResponse
import com.kotlindiscord.kord.extensions.modules.unsafe.types.ackPublic
import com.kotlindiscord.kord.extensions.modules.unsafe.types.respondPublic
import dev.kord.common.entity.Permission
import org.slf4j.LoggerFactory

class JokeExtension : Extension() {
    private val logger = LoggerFactory.getLogger(JokeExtension::class.java)

    override val name = "joke"

    private val jokes = listOf(
        "Why did the developer go broke? Because he used up all his cache!",
        "Why do programmers prefer dark mode? Because light attracts bugs!",
        "What do you call a programmer from Finland? Nerdic!",
        "Why do Java developers wear glasses? Because they can't C#!",
        "Why is DeuceCan the way that he is? Because he's a bot!"
    )

    @OptIn(UnsafeAPI::class)
    override suspend fun setup() {
        unsafeSlashCommand {
            name = "joke"
            description = "Get a random programming joke"
            initialResponse = InitialSlashCommandResponse.None

            check {
                hasPermission(Permission.SendMessages)
                isNotBot()
            }

            action {
                try {
                    ackPublic()
                    respondPublic {
                        content = jokes.random()
                    }
                } catch (e: Exception) {
                    respondPublic {
                        content = "Failed to fetch a joke. Try again later!"
                    }
                    logger.error("Joke command failed: ${e.message}")
                }
            }
        }
    }
}