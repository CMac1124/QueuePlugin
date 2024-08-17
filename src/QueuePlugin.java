import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class QueuePlugin extends JavaPlugin {
    private QueueManager queueManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        getLogger().info("QueuePlugin has been enabled!");

        // Initialize the queue and scoreboard managers
        queueManager = new QueueManager(this);
        scoreboardManager = new ScoreboardManager(this);

        // Register commands
        this.getCommand("qp").setExecutor(new QueuePriorityCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("QueuePlugin has been disabled!");
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
