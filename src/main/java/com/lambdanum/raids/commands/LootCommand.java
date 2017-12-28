package com.lambdanum.raids.commands;

import com.google.gson.Gson;
import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.model.LootItem;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class LootCommand implements ICommand {

    private static final String URL = "https://boiling-forest-57763.herokuapp.com/loot/";

    private Gson gson;

    public LootCommand(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String getName() {
        return "loot";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "loot";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            return;
        }

        String playerName = args[0];
        String lootTable = args[1];
        try {
            String lootJson = HttpHelper.get(URL + lootTable + "?username=" + playerName);
            LootItem[] lootItems = gson.fromJson(lootJson, LootItem[].class);
            for (LootItem item : lootItems) {
                EntityPlayer player = server.getPlayerList().getPlayerByUsername(playerName);
                player.addItemStackToInventory(new ItemStack(Item.getByNameOrId(item.minecraftId),item.amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3,"");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
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
