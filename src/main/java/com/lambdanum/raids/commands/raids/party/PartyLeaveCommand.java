package com.lambdanum.raids.commands.raids.party;

import com.lambdanum.raids.application.RaidPartyService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class PartyLeaveCommand implements ICommand {

    private RaidPartyService raidPartyService;

    public PartyLeaveCommand(RaidPartyService raidPartyService) {
        this.raidPartyService = raidPartyService;
    }

    @Override
    public String getName() {
        return "party-leave";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "party-leave";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        raidPartyService.removePlayerFromTheirParty(sender.getName());
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return raidPartyService.isPlayerInAParty(sender.getName()) && sender instanceof EntityPlayer;
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
