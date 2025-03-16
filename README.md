<p align="center">
  <img title="LightiBot Logo" src="https://i.imgur.com/9jSDjuc.jpeg" alt="LightiBot" width="100">
  <h1 align="center">LightiBot</h1>
</p>

<p align="center">
  Enhance Discord Server Interaction with Real-Time Voice Channel Join Notifications
  <br/>
  <a href="https://github.com/AyooEfrain/LightiBot/issues">Report Bug</a>
  ·
  <a href="https://github.com/AyooEfrain/LightiBot/issues">Request Feature</a>
</p>

![Project Status](https://img.shields.io/badge/Status-Development-yellow)
![License](https://img.shields.io/github/license/AyooEfrain/LightiBot)
![Last Commit](https://img.shields.io/github/last-commit/AyooEfrain/LightiBot)

## ✨ Overview

LightiBot is a Discord bot designed to foster better social interaction within your server by providing real-time notifications when users join voice channels. In larger communities or even smaller friend groups, it can be easy to miss when someone hops into a voice chat. LightiBot solves this by sending a customizable message to a designated text channel whenever a user connects to a voice channel. This simple yet effective feature encourages more spontaneous interactions and helps members know when others are available to chat.

## 🚀 Key Features

* **Real-Time Voice Channel Join Notifications:** Get instant alerts when a user joins any configured voice channel on your server.
* **Customizable Notification Messages:** Tailor the notification message to fit your server's tone and style. You can include user mentions, channel names, and more.
* **Configurable Monitored Channels:** Choose specific voice channels that LightiBot should monitor for join events. This allows you to focus notifications on relevant areas of your server.
* **Simple Setup:** Easy to configure and get running on your Discord server.
* **Lightweight and Efficient:** Designed to be resource-friendly and have minimal impact on your server's performance.

## 🛠️ Built With
* Kord - An Idiomatic Kotlin Wrapper for The Discord API
* Kord Extensions - An extensions framework for Kord
* **Kotlin - A cross-platform, statically typed, general-purpose programming language with type inference
* **SQLDelight - Typesafe APIs from SQL
* IntelliJ Ultimate - For coding and implementing

## ⚙️ How It Works

LightiBot connects to your Discord server and actively listens for voice state updates. When a user's voice state changes from being disconnected or in a different voice channel to joining a new one, LightiBot detects this change and sends a notification message to the designated text channel.

## ⚙️ Configuration

Once the bot is running, you'll likely need to configure which voice channels it should monitor and the text channel where notifications should be sent. This configuration might be done through:

* **Environment Variables:** You can add more variables to your `.env` file to specify channel IDs. (Alt. use systemd to perm run the bot and set env variables there.)
* **Discord Commands:** /configure channel , /configure notifications , /notify

## 🤝 Contributing

Contributions are welcome! If you have ideas for new features, improvements, or bug fixes, feel free to:

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Contact

lightefx - https://github.com/AyooEfrain

Project Link: [https://github.com/AyooEfrain/LightiBot](https://github.com/AyooEfrain/LightiBot)

---
