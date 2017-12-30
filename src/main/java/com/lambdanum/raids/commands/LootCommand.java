package com.lambdanum.raids.commands;

import com.lambdanum.raids.application.LootService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class LootCommand implements ICommand {

    private LootService lootService;

    public LootCommand(LootService lootService) {
        this.lootService = lootService;
    }

    @Override
    public String getName() {
        return "loot";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "loot";
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

        String playerName = args[0];
        String lootTable = args[1];

        lootService.asyncDistributeLoot(playerName, lootTable);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3,"");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand iCommand) {
        return 0;
    }
}
