# sChatChannels
A Spigot plugin for separating chat into private channels.
### Configuration
Configure/edit plugin messages as neccessary. 
Variables available:
- {DISPLAYNAME} - returns player's displaynamee
- {USERNAME} - returns player's username
- {CHANNEL} - returns channel's display name
```
no-access: "&cYou do not have permission to access this channel!"
no-msg: "&cPlease include a message!"
msg-sent: "&cMessage sent to {CHANNEL}!" #confirmation if player doesnt have receive access
toggled-on: "&bToggled {CHANNEL} &2on."
toggled-off: "&bToggled {CHANNEL} &coff."
none-online: "&cNo one with access to this channel is online!"
```
Three different channels are configured by default: staffchat, adminchat, and helpme. Channels allow users to send/receive messages to/from a limited group of people (depending on their permissions). Unless you give a player permission, they won't be able to send/receive any messages from any channel. 
```
permanent-channels:
    staffchat: #use /staffchat (or an alias) to access
        display-name: "StaffChat"
        format: "&8[&cStaff&8]: &7<{DISPLAYNAME}&7> &e{MESSAGE}"
        aliases:
            - sc
            - schat
    adminchat:
        display-name: "Staff"
        format: "&8[&cAdmin&8]: &7<{DISPLAYNAME}&7> &e{MESSAGE}"
        aliases:
            - ac
            - achat
    helpme:
        display-name: "HelpMe"
        format: "&e{USERNAME}&e needs help:&7 {MESSAGE}"
        toggleable: false
```
### Commands
Use the channel name (ex: staffchat, adminchat, helpme, etc.) to send a message to the channel. Channel aliases can also be used as the command.

For example, to use adminchat, you'd type /adminchat (message) or /ac (message) or /achat (message). The plugin also support channel toggling, meaning that if you type the command without any messages, it would toggle your chat to automatically use the channel's chat (this can be disabled by setting "toggleable" to false) without having to use the command.

### Permissions

To have send access to a channel: channels.channel.{channelname}.send 
To have receive access to a channel: channels.channel.{channelname}.receive 

### Newbies Guide
1. Download Plugin
2. Give all player the "channels.channel.helpme.send" permission and "channels.channel.helpme.receive" to all staff groups. This allows them to be able to message any online staff with /helpme.
3. Give all staff the "channels.channel.staffchat.send" and "channels.channel.staffchat.receive" permissions. Now staff can talk to other staff privately with /staffchat
4. Give admins the "channels.channel.adminchat.send" and "channels.channel.adminchat.receive" permissions.
5. If you're having trouble with this plugin, PM me.

