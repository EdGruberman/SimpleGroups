package edgruberman.bukkit.simplegroups;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import edgruberman.bukkit.messagemanager.Main;
import edgruberman.bukkit.messagemanager.MessageLevel;

public class CommandManager implements CommandExecutor {
    
    private JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        
        this.plugin.getCommand("group").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        Main.messageManager.log(MessageLevel.FINE
                , ((sender instanceof Player) ? ((Player) sender).getName() : "CONSOLE")
                + " issued command: " + label + " " + this.join(split)
        );
        
        if (split.length < 2) {
            Main.messageManager.respond(sender, MessageLevel.SEVERE, "Syntax Error: /group <Group> reload");
            return true;
        }
        
        Group group = GroupManager.getGroup(split[0], true);
        if (group == null) {
            Main.messageManager.respond(sender, MessageLevel.SEVERE, "Group \"" + split[0] + "\" not found.");
            return true;
        }
        
        return true;
    }
    
    private String join(String[] s) {
        return this.join(Arrays.asList(s), " ");
    }
    
    private String join(List<String> list, String delim) {
        if (list == null || list.isEmpty()) return "";
     
        StringBuilder sb = new StringBuilder();
        for (String s : list) sb.append(s + delim);
        sb.delete(sb.length() - delim.length(), sb.length());
        
        return sb.toString();
    }
}