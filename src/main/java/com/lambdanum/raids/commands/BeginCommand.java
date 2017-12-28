package com.lambdanum.raids.commands;

import com.google.gson.Gson;
import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.application.PlayerHomeService;
import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.User;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

public class BeginCommand implements ICommand {

    private LootCommand lootCommand;
    private PlayerHomeService homeService;
    private HttpHelper httpHelper;

    private static final String API_URL = "https://boiling-forest-57763.herokuapp.com/user/";
    private Gson gson;

    public BeginCommand(LootCommand lootCommand, PlayerHomeService homeService, HttpHelper httpHelper, Gson gson) {
        this.lootCommand = lootCommand;
        this.homeService = homeService;
        this.httpHelper = httpHelper;
        this.gson = gson;
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

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {
                            server.getWorld(0).setBlockState(playerPos.add(2-i,2+j,1-k),Blocks.LEAVES.getDefaultState());
                        }
                    }
                }

                for (int j = 0; j < 5; j++) {
                    server.getWorld(0).setBlockState(playerPos.add(1, j, 0), Blocks.LOG.getDefaultState());
                }
                setUserAlreadyGeneratedIsland(playerName);
                player.setGameType(GameType.SURVIVAL);

                player.setPosition(playerPos.getX(), playerPos.getY() + 2, playerPos.getZ());
                lootCommand.execute(server, sender, new String[] {playerName, "startup"});
                homeService.asyncSetPlayerHome(playerName, new Position(playerPos.getX()-1, playerPos.getY() + 1, playerPos.getZ()));
                homeService.asyncTeleportPlayerToHome(player);
            } else {
                player.setGameType(GameType.SPECTATOR);
                sender.sendMessage(new TextComponentString("You are now spectating. Move to the desired location and " +
                                "call the /begin command again to generate your island at that location."));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (!(sender instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer) sender;
        return player.dimension == 0 && !hasUserAlreadyGeneratedIsland(player.getName());
    }

    private boolean hasUserAlreadyGeneratedIsland(String username) {
        try {
            String userJson = httpHelper.get(API_URL + username);
            User user = gson.fromJson(userJson, User.class);
            return user.generatedIsland;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setUserAlreadyGeneratedIsland(String username) {
        try {
            httpHelper.post(API_URL + username + "/isGeneratedIsland");
        } catch (Exception e) {
            e.printStackTrace();
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
