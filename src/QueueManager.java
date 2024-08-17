import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class QueueManager {
    private final Map<Player, Integer> queue = new HashMap<>();
    private final Map<String, Integer> rankPriorities = new HashMap<>();
    private final PriorityQueue<Player> priorityQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(queue.get(p1), queue.get(p2)));
    private final JavaPlugin plugin;

    public QueueManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addToQueue(Player player) {
        // Retrieve the player's rank from PermissionsEx and determine their queue priority
        String group = PermissionsEx.getUser(player).getGroups().get(0).getName();
        int priority = rankPriorities.getOrDefault(group, 20);

        queue.put(player, priority);
        priorityQueue.add(player);

        // Update the scoreboard with the new queue position
        updateScoreboard(player);
    }

    private int getPriorityFromGroup(String group) {
        return rankPriorities.getOrDefault(group, 20); // Default to lowest priority
    }

    public int getQueuePosition(Player player) {
        return new ArrayList<>(priorityQueue).indexOf(player) + 1;
    }

    private void updateScoreboard(Player player) {
        // Call the ScoreboardManager to update the scoreboard
        int position = getQueuePosition(player);
        int total = priorityQueue.size();
        ((ScoreboardManager) plugin.getScoreboardManager()).updateScoreboard(player, position, total);
    }

    public void updateRankPriorities(Map<String, Integer> newRankPriorities) {
        rankPriorities.clear();
        rankPriorities.putAll(newRankPriorities);
    }
}
