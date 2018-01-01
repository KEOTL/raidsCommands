package com.lambdanum.raids.commands.raids;

import com.lambdanum.raids.application.RaidPartyService;
import com.lambdanum.raids.application.RaidService;
import com.lambdanum.raids.infrastructure.persistence.PlayerNotInsideAPartyException;
import com.lambdanum.raids.raid.RaidAlreadyActiveOnDimensionException;
import com.lambdanum.raids.raid.controller.DisallowedItemsInInventoryException;
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
    private RaidPartyService raidPartyService;

    public StartRaidCommand(RaidService raidService, RaidPartyService raidPartyService) {
        this.raidService = raidService;
        this.raidPartyService = raidPartyService;
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
        } catch (DisallowedItemsInInventoryException e) {
            throw new CommandException("One or more players are carrying disallowed items. Please empty your inventory before entering a raid.");
        } catch (PlayerNotInsideAPartyException e) {
            throw new CommandException("You must create a party first! Use /party-create to get started.");
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
