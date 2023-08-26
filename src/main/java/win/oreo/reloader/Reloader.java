package win.oreo.reloader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.HashMap;

public final class Reloader extends JavaPlugin {

    HashMap<File, Long> map = new HashMap<>();

    @Override
    public void onEnable() {
        run();
    }

    static BukkitTask task;

    public void run() {
         task = Bukkit.getScheduler().runTaskTimer(this, () -> {
            File dir = new File("plugins/");
            File[] files = dir.listFiles();
            for (File f : files) {
                Bukkit.getConsoleSender().sendMessage(f.getName());
            }

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (!map.containsKey(file)) {
                            map.put(file, file.lastModified());
                        } else {
                            if (map.get(file) != file.lastModified()) {
                                task.cancel();
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "RELOAD STARTED!");
                                Bukkit.reload();
                            }
                        }
                    }
                }
            }
        }, 0, 40);
    }
}
