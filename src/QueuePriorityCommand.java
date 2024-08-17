import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permission;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class QueuePriorityCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final Map<String, Integer> rankPriorities = new HashMap<>();

    public QueuePriorityCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("qp")) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /qp <rank> <priority>");
                return false;
            }

            String rank = args[0];
            int priority;

            try {
                priority = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Priority must be a number.");
                return false;
            }

            if (priority < 1 || priority > 20) {
                sender.sendMessage("Priority must be between 1 and 20.");
                return false;
            }

            // Store the priority for the rank
            rankPriorities.put(rank, priority);

            sender.sendMessage("Set priority for rank " + rank + " to " + priority + ".");

            // Update the QueueManager with new priorities
            ((QueuePlugin) plugin).getQueueManager().updateRankPriorities(rankPriorities);

            return true;
        }
        return false;
    }
}
