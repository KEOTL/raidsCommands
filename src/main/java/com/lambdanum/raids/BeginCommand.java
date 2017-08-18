package com.lambdanum.raids;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BeginCommand implements ICommand {

    private LootCommand lootCommand = new LootCommand();

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
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)sender;

            if (player.isSpectator()) {

                String playerName = player.getName();
                BlockPos playerPos = player.getPosition();

                for (int i = 0; i < 6; i++) {
                    server.getWorld(0).setBlockState(playerPos.add(i - 3, -1, 0), Blocks.GRASS.getDefaultState());
                    server.getWorld(0).setBlockState(playerPos.add(i - 3, -1, -1), Blocks.GRASS.getDefaultState());
                    server.getWorld(0).setBlockState(playerPos.add(i - 3, -1, 1), Blocks.GRASS.getDefaultState());
                    for (int j = 1; j < 3; j++) {
                        server.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, 0), Blocks.DIRT.getDefaultState());
                        server.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, -1), Blocks.DIRT.getDefaultState());
                        server.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, 1), Blocks.DIRT.getDefaultState());
                    }
                }
                for (int j = 0; j < 5; j++) {
                    server.getWorld(0).setBlockState(playerPos.add(1, j, 0), Blocks.LOG.getDefaultState());
                }

                player.setGameType(GameType.SURVIVAL);
                player.setPosition(playerPos.getX(), playerPos.getY() + 1, playerPos.getZ());
                lootCommand.execute(server, sender, new String[] {playerName, "startup"});
            } else {
                player.setGameType(GameType.SPECTATOR);
                sender.sendMessage(new TextComponentString("You are now spectating. Move to the desired location and " +
                                "call the /begin command again to generate your island at that location."));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (((EntityPlayer)sender).dimension == 0) {
            return true; //TODO check that they are generating an island for the first time.
        } else {
            return false;
        }
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
