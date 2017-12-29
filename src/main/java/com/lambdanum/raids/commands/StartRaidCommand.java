package com.lambdanum.raids.commands;

import com.lambdanum.raids.application.OnlinePlayerService;
import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.MinecraftBroadcastLogger;
import com.lambdanum.raids.raid.controller.RaidController;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.party.Party;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class StartRaidCommand implements ICommand {

    private RaidControllerProvider raidControllerProvider;
    private McLogger logger;
    private OnlinePlayerService onlinePlayerService;

    public StartRaidCommand(RaidControllerProvider raidControllerProvider, MinecraftBroadcastLogger logger, OnlinePlayerService onlinePlayerService) {
        this.raidControllerProvider = raidControllerProvider;
        this.logger = logger;
        this.onlinePlayerService = onlinePlayerService;
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
        int playDimension = 0;

        String raidName = args[0];
        if (args.length > 1) {
            playDimension = Integer.parseInt(args[1]);
        }
        List<String> playerNames = new ArrayList<>();

        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                playerNames.add(args[i]);
            }
        } else {
            playerNames.add(sender.getName());
        }

        if (raidControllerProvider.isRaidActiveInDimension(playDimension)) {
            logger.log("warning: raid already active in dimension " + playDimension + ". Aborting.");
            return;
        }

        List<EntityPlayer> players = playerNames.stream().map(onlinePlayerService::getPlayerByUsername).collect(Collectors.toList());
        RaidController controller = raidControllerProvider.createController(raidName, playDimension, new Party(players));
        controller.startMapInitialization();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3, "");
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
