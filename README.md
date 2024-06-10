ACDiscordBot-Java
==========
A Discord bot that uses the Java Discord API
--------------------------------------------------

This is an attempt to make a Discord bot that uses a [Java Discord API](https://github.com/discord-jda/JDA). This makes
it is easier to develop
for developers used to writing programs in Java. It is used in multiple of Tennessene's Discord servers. Currently, it
is
still in an earlier stage of development, so expect bugs and incomplete features.

Compiling from Source
------
To compile ACDiscordBot, you will need JDK 17 or later, an internet connection, and preferably an IDE (IntelliJ is the
best).

If you don't want to use an IDE, clone this repo and run `./gradlew build`. You can find the compiled jar in `build/lib`
as `ACDiscordBot-<version>.jar`. Otherwise, set up a new project that is from version control, set it to use this repo,
and add a configuration that runs `gradle build`.

Binaries
------
You can download the jar files in the releases tab if you don't want to compile ACDiscordBot from source. Keep in mind
it won't always be up-to-date with the latest commit.

(c) 2021-2024 Anston Sorensen
