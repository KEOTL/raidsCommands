package com.lambdanum.raids.commands.raids.party;

import com.lambdanum.raids.application.RaidPartyService;
import com.lambdanum.raids.infrastructure.persistence.PlayerNotInsideAPartyException;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class TellPartyCommand implements ICommand {

    private RaidPartyService raidPartyService;

    public TellPartyCommand(RaidPartyService raidPartyService) {
        this.raidPartyService = raidPartyService;
    }

    @Override
    public String getName() {
        return "party";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/party <message...>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException(getUsage(sender));
        }
        String message = StringUtils.join(args, " ");
        try {
            raidPartyService.tellParty(sender.getName(), message);
        } catch (PlayerNotInsideAPartyException e) {
            throw new CommandException("You are not in a party!");
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
