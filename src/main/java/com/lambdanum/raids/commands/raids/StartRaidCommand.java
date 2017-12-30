package com.lambdanum.raids.commands.raids;

import com.lambdanum.raids.application.RaidService;
import com.lambdanum.raids.raid.RaidAlreadyActiveOnDimensionException;
import com.lambdanum.raids.raid.controller.IncorrectNumberOfPlayersException;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class StartRaidCommand implements ICommand {

    private RaidService raidService;

    public StartRaidCommand(RaidService raidService) {
        this.raidService = raidService;
    }

    @Override
    public String getName() {
        return "start-raid";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "start-raid <raid-name> <play-dimension> <players...>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }
        String raidName = args[0];
        try {
            raidService.startRaid(sender, raidName);
        } catch (RaidAlreadyActiveOnDimensionException e) {
            throw new CommandException("Raid already active on play dimension. (No play dimensions available.) Aborting.");
        } catch (IncorrectNumberOfPlayersException e) {
            throw new CommandException("Incorrect number of players. Your party is either too large or too small.");
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
