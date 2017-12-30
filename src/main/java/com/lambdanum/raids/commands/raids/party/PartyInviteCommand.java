package com.lambdanum.raids.commands.raids.party;

import com.lambdanum.raids.application.RaidPartyService;
import com.lambdanum.raids.raid.controller.party.OnlinePlayerNotFoundException;
import com.lambdanum.raids.raid.controller.party.PlayerAlreadyInAPartyException;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class PartyInviteCommand implements ICommand {

    private RaidPartyService raidPartyService;

    public PartyInviteCommand(RaidPartyService raidPartyService) {
        this.raidPartyService = raidPartyService;
    }

    @Override
    public String getName() {
        return "party-invite";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "party-invite <player>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("invalid syntax");
        }
        String hostPlayer = sender.getName();
        String invitedPlayer = args[0];
        try {
            raidPartyService.inviteToParty(hostPlayer, invitedPlayer);
        } catch (PlayerAlreadyInAPartyException e) {
            throw new CommandException("Player is already in a party!");
        } catch (OnlinePlayerNotFoundException e) {
            throw new CommandException("Could not find the required player.");
        }
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
