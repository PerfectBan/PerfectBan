# 🔒 PerfectBan - Minecraft Ban System 🔒

Works with BungeeCord. Customizable to the last detail.

![versions](https://img.shields.io/badge/versions-1.8.x%20--%201.17.x-blue)
![bungee](https://img.shields.io/badge/platform-bungeecord-blue)

## SpigotMC Page

Visit our plugin page [here][spigotmc] for more Information

## Setup & Configuration

This plugin works based on MySQL & Hibernate. You will have to set up your own database in order to get the plugin to work. Your database connection settings can be accessed in the plugin folder under `database.yml`

If you don't know what Hibernate is, please download the latest version of our shaded plugin [here][plugin_shaded]. If however, you do know what Hibernate is you can install it on your server and download the unshaded plugin [here][plugin_unshaded]

### Need help setting up a database?

Visit [this link][mysqlhelp], then enter your connection data in the `database.yml` file

## Commands & Permissions

**The specific command, as well as the description and permission for each of these actions can be changed in the `commands.yml` file**

![BAN](https://img.shields.io/static/v1?label=1.&message=Ban%20Command&color=blue&style=for-the-badge)

Basic ban command, supports specific time inputs

#### Usage & Example
```
Usage: /ban <Player> <Reason> <Time>
Example: /ban MinecraftPlayer Cheating 30d
```

**If no value is given for the time, the player will be banned forever**

![UNBAN](https://img.shields.io/static/v1?label=2.&message=Unban%20Command&color=blue&style=for-the-badge)

Basic unban command, support for unban reason visible in players history


#### Usage & Example
```
Usage: /unban <Player> <Reason>
Example: /unban MinecraftPlayer Wrong Name
```

![CHANGEBAN](https://img.shields.io/static/v1?label=3.&message=Change%20Ban%20Command&color=blue&style=for-the-badge)

Command for changing a players ban

#### Usage & Example
```
Usage: /changeban <Player> <Reason>
Example: /changeban MinecraftPlayer 14d
```

**Note that these changes will be logged in history**


[spigotmc]: https://www.spigotmc.org
[mysqlhelp]: https://dev.mysql.com/doc/mysql-getting-started/en/
[plugin_shaded]: https://www.spigotmc.org
[plugin_unshaded]: https://www.spigotmc.org
