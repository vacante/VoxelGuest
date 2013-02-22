package com.thevoxelbox.voxelguest.modules.helper.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.thevoxelbox.voxelguest.modules.helper.HelperModule;
import com.thevoxelbox.voxelguest.modules.helper.ReviewRequest;

public class HelperReviewCommand implements TabExecutor
{
    private final HelperModule module;

    public HelperReviewCommand(final HelperModule module)
    {
        this.module = module;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;
            if (this.module.getManager().isHelper(player))
            {
                if (args.length >= 1)
                {
                    if (args[0].equalsIgnoreCase("-c"))
                    {
                        String comment = "";
                        for (int i = 1; i > args.length; i++)
                        {
                            comment += args[i];
                            comment += " ";
                        }
                        comment.trim();
                        this.module.getManager().addComment(player, comment);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("-h"))
                    {
                        if (args.length == 2)
                        {
                            final List<Player> matches = Bukkit.matchPlayer(args[1]);
                            if (matches.size() == 1)
                            {
                                this.module.getManager().sendHelperGuestHistory(player, matches.get(0).getName());
                            }
                            else
                            {
                                if (matches.size() > 1)
                                {
                                    sender.sendMessage(ChatColor.DARK_RED + "Multiple matches found for \"" + args[1] + "\"");
                                    return true;
                                }
                                else
                                {
                                    sender.sendMessage(ChatColor.DARK_RED + "No matches found for \"" + args[1] + "\"");
                                    return true;
                                }
                            }
                        }
                        else
                        {
                            sender.sendMessage("Please specify a player!");
                            return true;
                        }
                    }
                    List<Player> matches = Bukkit.matchPlayer(args[0]);
                    if (matches.size() == 1)
                    {
                        final Player guest = matches.get(0);
                        final ReviewRequest review = this.module.getManager().getReview(guest);
                        if (review != null)
                        {
                            player.teleport(review.getLoc());
                            guest.teleport(player);
                            this.module.getManager().closeReview(player, review);
                            this.module.getManager().sendHelperGuestHistory(player, guest.getName());
                            return true;
                        }
                    }
                    else
                    {
                        if (matches.size() > 1)
                        {
                            sender.sendMessage(ChatColor.DARK_RED + "Multiple matches found for \"" + args[1] + "\"");
                            return true;
                        }
                        else
                        {
                            sender.sendMessage(ChatColor.DARK_RED + "No matches found for \"" + args[1] + "\"");
                            return true;
                        }
                    }
                }
                else
                {
                    String reviews = this.module.getManager().getActiveRequests();
                    if (reviews != null)
                    {
                        player.sendMessage(reviews);
                        return true;
                    }
                    else
                    {
                        player.sendMessage("There are currenly no WL reviews");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
