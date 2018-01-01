# raidsCommands
## Party Commands
- `party-create` : Create a party.
- `party-info` : Lists players in the party.
- `party-leave` : Leave the party.
- `party-invite` : Invite someone to join your party. 
## Raids Commands
- `start-raid <raid-name>` : Starts a raid. Requires a party.
- `exit-raid` : Quit the current raid. (Can be used as a way to escape a crashed raid.)
## Objectives
### General Syntax
```
/objective <objective-name> <objective args...> ?? <triggered command 1> ;; <triggered command 2>
```
### Available Objectives
- `ask-item` : Waits for a dropped item on a given tile.
```
/objective ask-item <item:name> <x> <y> <z>
```
- `bring-player` : Waits for a player to walk to a specific tile.
```
/objective bring-player <x> <y> <z>
```
- `timer` : Waits for a specified number of seconds.
```
/objective timer <number of seconds>
```
- `random-timer` : Waits for a random number of seconds within a given range.
```
/objective random-timer <min> <max>
```
## Raid File syntax
```json
{
  "name": "raid-0",
  "backupDimension": 30003,
  "region": {
    "lowerCorner": {
      "x": 0,
      "y": 0,
      "z": 0
    },
    "higherCorner": {
      "x": 15,
      "y": 100,
      "z": 10
    }
  },
  "spawn": {
    "x": 2,
    "y": 62,
    "z": 2
  },
  "startupScript": [
    "time set 0",
    "objective ask-item minecraft:stick 9 61 2 ?? say he was a drummer at heart. ;; setblock 10 61 2 minecraft:redstone_block ;; victory"
  ],
  "victoryScript": [
    "say All heroes may rest in peace."
  ],
  "defeatScript": [],
  "rewardLootTableName": "raid-0",
  "lootStrategy": "IDENTICAL",
  "minPlayers": 1,
  "maxPlayers": 10,
  "allowedItems" : []
}
```
