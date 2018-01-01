# raidsCommands

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
