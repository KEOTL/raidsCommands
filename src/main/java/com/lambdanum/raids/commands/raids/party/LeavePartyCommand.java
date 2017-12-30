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

public class LeavePartyCommand implements ICommand {

    private RaidPartyService partyService;

    public LeavePartyCommand(RaidPartyService partyService) {
        this.partyService = partyService;
    }

    @Override
    public String getName() {
        return "leave-party";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "leave-party";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        partyService.removePlayerFromTheirParty(sender.getName());
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && partyService.isPlayerInAParty(sender.getName());
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
