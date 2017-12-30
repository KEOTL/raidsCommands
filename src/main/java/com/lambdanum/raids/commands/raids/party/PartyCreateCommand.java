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
import net.minecraft.util.text.TextComponentString;

public class PartyCreateCommand implements ICommand {

    private RaidPartyService raidPartyService;

    public PartyCreateCommand(RaidPartyService raidPartyService) {
        this.raidPartyService = raidPartyService;
    }

    @Override
    public String getName() {
        return "party-create";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "party-create";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        if (raidPartyService.isPlayerInAParty(player.getName())) {
            sender.sendMessage(new TextComponentString("You are already in a party."));
        } else {
            raidPartyService.createParty(player);
            sender.sendMessage(new TextComponentString("Party created."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && ((EntityPlayer) sender).dimension == 0;
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
