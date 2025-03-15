package extensions.joke

import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.checks.isNotBot
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.modules.unsafe.annotations.UnsafeAPI
import com.kotlindiscord.kord.extensions.modules.unsafe.extensions.unsafeSlashCommand
import com.kotlindiscord.kord.extensions.modules.unsafe.types.InitialSlashCommandResponse
import com.kotlindiscord.kord.extensions.modules.unsafe.types.respondPublic
import dev.kord.common.entity.Permission

class JokeExtension : Extension() {
    override val name = "joke"

    private val jokes = listOf(
        "Why did the developer go broke? Because he used up all his cache!",
        "Why do programmers prefer dark mode? Because light attracts bugs!",
        "What do you call a programmer from Finland? Nerdic!",
        "Why is deucecan the way the he is? Because he is a deucecan!"
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
                respondPublic {
                    content = jokes.random()
                }
            }
        }
    }
}