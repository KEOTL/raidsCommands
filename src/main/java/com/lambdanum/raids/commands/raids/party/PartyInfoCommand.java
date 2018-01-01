package com.lambdanum.raids.commands.raids.party;

import com.lambdanum.raids.application.RaidPartyService;
import com.lambdanum.raids.minecraft.OnlinePlayerRepository;

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

public class PartyInfoCommand implements ICommand {

    private RaidPartyService raidPartyService;
    private OnlinePlayerRepository onlinePlayerRepository;

    public PartyInfoCommand(RaidPartyService raidPartyService, OnlinePlayerRepository onlinePlayerRepository) {
        this.raidPartyService = raidPartyService;
        this.onlinePlayerRepository = onlinePlayerRepository;
    }

    @Override
    public String getName() {
        return "party-info";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "party-info";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!raidPartyService.isPlayerInAParty(sender.getName())) {
            throw new CommandException("You are not in a party!");
        }
        sender.sendMessage(new TextComponentString("§6" + "Currently in your party :"));
        for (String member : raidPartyService.getPartyMembers(sender.getName())) {
            if (onlinePlayerRepository.isPlayerOnline(member)) {
                sender.sendMessage(new TextComponentString("§6" + member));
            } else {
                sender.sendMessage(new TextComponentString("§c" + member + "(Offline)"));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer;
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
