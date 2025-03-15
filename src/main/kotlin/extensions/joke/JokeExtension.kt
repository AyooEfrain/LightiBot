package extensions.joke

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.commands.application.slash.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.checks.isNotBot
import dev.kord.common.entity.Permission

class JokeExtension : Extension() {
    override val name = "joke"

    private val jokes = listOf(
        "Why did the developer go broke? Because he used up all his cache!",
        "Why do programmers prefer dark mode? Because light attracts bugs!",
        "What do you call a programmer from Finland? Nerdic!"
    )

    override suspend fun setup() {
        ephemeralSlashCommand(::Unit) {
            name = "joke"
            description = "Get a random programming joke"

            check {
                hasPermission(Permission.SendMessages)
                isNotBot()
            }

            action {
                respond {
                    content = jokes.random()
                }
            }
        }
    }

    class Unit : Arguments()
}