# VelocityPlus

Join my Discord (German)  
[![Join my Discord (German)](https://img.shields.io/discord/674745039972466688.svg?logo=discord&label=)](https://discord.x-community.net)

A Velocity proxy plugin which adds customizable commands from the bungeecord proxy and a little bit more.

### !! It's my first velocity plugin, so please don't hate me for it. !!

## Commands

* Alert  
  * Permission: `vplus.alert`
  * Description: `Send a message to every user which is connected to the proxy. It supports MiniMessage.` [Link](https://docs.adventure.kyori.net/minimessage/index.html)
  * Usage: `/alert <Message>`
* Find
  * Permission: `vplus.find`
  * Description: `Find a player which is connected to the proxy. Good, if you want to see, which server someone is on.`
  * Usage: `/find <Player>`
* Goto
  * Permission: `vplus.goto`
  * Description: `Go to a player, which is connected to a different server. (Only Server switching. Not teleporting to their location)`
  * Usage: `/goto <Player>`
* IP
  * Permission: `vplus.ip`
  * Description: `Get the IP of a connected Player.`
  * Usage: `/ip <Player>`
* Kickall
  * Permission: `vplus.kickall`, `vplus.kickall.bypass` (Bypass the Kickall Command), `vplus.kickall.force` (Kick all. Bypass Users included), `vplus.kickall.servername` (Per server permission)
  * Description: `Kick all players from the proxy, or from a specific server. There is a bypass Permission for the default and per server kickall command. If you use --force and have the vplus.kickall.force Permission, then you can kick even the Players which have the bypass permission.`
  * Usage: `/kickall {Server} {--force}` {} = Optional
* Send
  * Permission: `vplus.send.all`, `vplus.send.current`, `vplus.send.server`, `vplus.send.user`
  * Description: `Send a player to another server. You can send all players which are connected to the proxy, all players which are connected to your current server, all players which are connected to a different browser or single users to another server.`
  * Usage: `/send <all, current, Server, User> <Target Server>`

## Planned features
- [x] Alert Command
- [x] Find Command
- [x] Goto Command
- [x] IP Command
- [x] Kickall Command
- [x] Send Command
- [x] Added configurable texts
- [x] Configuration file with texts and prefix. (Alert command has its own prefix)
- [x] Implement format parser to Alert command
- [x] Implement format parser to Kickall command
- [ ] Implement format parser to configs for advanced text formatting.
- [ ] Add placeholder function for every text. (I may add placeholderAPI support, but I'm not sure)
