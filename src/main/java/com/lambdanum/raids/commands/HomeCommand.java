package com.lambdanum.raids.commands;

import com.google.gson.Gson;
import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.model.Position;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class HomeCommand implements ICommand {

    private String API_URL = "https://boiling-forest-57763.herokuapp.com/home/";
    private Gson gson = new Gson();

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

            Position playerHome = getHomeForPlayer(player.getName());
            player.attemptTeleport(playerHome.getX(), playerHome.getY(), playerHome.getZ());
        }
    }

    public Position getHomeForPlayer(String username) {
        try {
            String jsonPosition = HttpHelper.get(API_URL + username);
            return gson.fromJson(jsonPosition,Position.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Position();
    }

    public void setHomeForPlayer(String username, Position homePosition) {
        try {
            HttpHelper.post(API_URL + username + String.format("?x=%d&y=%d&z=%d",homePosition.getX(),homePosition.getY(),homePosition.getZ()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return ((EntityPlayer)sender).dimension == 0;
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
