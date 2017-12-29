package com.lambdanum.raids.commands;

import com.lambdanum.raids.application.SkyblockService;
import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.skyblock.SkyblockUserRepository;

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
import net.minecraft.world.GameType;

public class BeginCommand implements ICommand {

    private SkyblockService skyblockService;
    private SkyblockUserRepository skyblockUserRepository;

    public BeginCommand(SkyblockService skyblockService, SkyblockUserRepository skyblockUserRepository) {
        this.skyblockService = skyblockService;
        this.skyblockUserRepository = skyblockUserRepository;
    }

    @Override
    public String getName() {
        return "begin";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "begin";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;

        if (player.isSpectator()) {
            String playerName = player.getName();
            BlockPos playerPos = player.getPosition();

            skyblockService.asyncSetupPlayerIsland(playerName, new Position(playerPos));

        } else {
            player.setGameType(GameType.SPECTATOR);
            sender.sendMessage(new TextComponentString("You are now spectating. Move to the desired location and " +
                "call the /begin command again to generate your island at that location."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (!(sender instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer) sender;
        return player.dimension == 0 && !skyblockUserRepository.hasUserAlreadyGeneratedIsland(player.getName());
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
