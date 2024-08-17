import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.DisplaySlot;

public class ScoreboardManager {
    private final JavaPlugin plugin;

    public ScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreboard(Player player, int position, int total) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        Objective objective = scoreboard.getObjective("queue");

        if (objective == null) {
            objective = scoreboard.registerNewObjective("queue", "dummy", "Queue Position");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        // Clear existing scores
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        // Display rank and queue position
        objective.getScore("Rank: " + getRank(player)).setScore(1);
        objective.getScore("Queue: " + position + "/" + total).setScore(0); // Position the queue info at the bottom

        player.setScoreboard(scoreboard);
    }

    private String getRank(Player player) {
        // Placeholder method for getting rank info
        // You might need to adapt this to match DeluxeHub's format
        String group = PermissionsEx.getUser(player).getGroups().get(0).getName();
        return "Rank: " + group; // Example format, adapt as needed
    }

    public static ScoreboardManager getInstance() {
        return new ScoreboardManager(Bukkit.getPluginManager().getPlugin("QueuePlugin"));
    }
}
