package com.lambdanum.raids;

import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LootCommand implements ICommand {

    private static final String URL = "https://boiling-forest-57763.herokuapp.com/loot/";

    private Gson gson = new Gson();

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
            String lootJson = HttpGet.getHTML(URL + lootTable);
            LootItem[] lootItems = gson.fromJson(lootJson, LootItem[].class);
            for (LootItem item : lootItems) {
                EntityPlayer player = server.getPlayerList().getPlayerByUsername(playerName);
                player.addItemStackToInventory(new ItemStack(Item.getByNameOrId(item.getMinecraftId()),item.getAmount()));
                //server.commandManager.executeCommand(sender, "give " + playerName + " " + item.getMinecraftId() + " " + item.getAmount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender.canUseCommand(3,"")) {
            System.out.println("Can use command");
        } else {
            System.out.println("Cannot use command");
        }
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
