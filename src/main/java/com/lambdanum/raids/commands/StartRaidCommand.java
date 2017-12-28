package com.lambdanum.raids.commands;

import com.lambdanum.raids.controller.RaidController;
import com.lambdanum.raids.controller.RaidControllerProvider;
import com.lambdanum.raids.util.MinecraftBroadcastLogger;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class StartRaidCommand implements ICommand {

    private RaidControllerProvider raidControllerProvider;
    private MinecraftBroadcastLogger logger;

    public StartRaidCommand(RaidControllerProvider raidControllerProvider, MinecraftBroadcastLogger logger) {
        this.raidControllerProvider = raidControllerProvider;
        this.logger = logger;
    }

    @Override
    public String getName() {
        return "start-raid";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "start-raid <raid-name> <play-dimension>";
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

        if (raidControllerProvider.isRaidActiveInDimension(playDimension)) {
            logger.log("warning: raid already active in dimension " + playDimension + ". Aborting.");
            return;
        }

        RaidController controller = raidControllerProvider.createController(raidName, playDimension);
        controller.startMapInitialization(server);
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
