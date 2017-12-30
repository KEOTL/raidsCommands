package com.lambdanum.raids.commands.raids.party;

import com.lambdanum.raids.application.RaidPartyService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class PartyInfoCommand implements ICommand {

    private RaidPartyService raidPartyService;

    public PartyInfoCommand(RaidPartyService raidPartyService) {
        this.raidPartyService = raidPartyService;
    }

    @Override
    public String getName() {
        return "party";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "party";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Currently in your party :"));
        for (String member : raidPartyService.getPartyMembers(sender.getName())) {
            sender.sendMessage(new TextComponentString(member));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return raidPartyService.isPlayerInAParty(sender.getName());
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
    public int compareTo(ICommand iCommand) {
        return 0;
    }
}
