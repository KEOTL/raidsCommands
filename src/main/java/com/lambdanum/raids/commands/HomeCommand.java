package com.lambdanum.raids.commands;

import com.lambdanum.raids.application.PlayerHomeService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class HomeCommand implements ICommand {

    private PlayerHomeService homeService;

    public HomeCommand(PlayerHomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "home";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            homeService.teleportPlayerToHome(player);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return ((EntityPlayer) sender).dimension == 0;
        }
        return false;
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
