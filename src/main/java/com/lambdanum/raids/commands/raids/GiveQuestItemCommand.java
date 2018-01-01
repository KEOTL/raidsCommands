package com.lambdanum.raids.commands.raids;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGive;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class GiveQuestItemCommand implements ICommand {
    @Override
    public String getName() {
        return "quest-give";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "quest-give <player> <item:id> [quantity]";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            return;
        }
        String player = args[0];
        String itemName = args[1];
        String amount;
        if (args.length < 3) {
            amount = "1";
        } else {
            amount = args[2];
        }
        String variant;
        if (args.length < 4) {
            variant = "0";
        } else {
            variant = args[3];
        }
        String rawCommand = "give " + player + " " + itemName + " " + amount + " " + variant + " {display:{Lore:[\"Quest Item\"]}}";
        sender.sendMessage(new TextComponentString(rawCommand));
        server.commandManager.executeCommand(sender, rawCommand);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return new CommandGive().checkPermission(server, sender);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
