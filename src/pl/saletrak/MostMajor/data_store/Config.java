package pl.saletrak.MostMajor.data_store;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class Config extends YamlConfiguration {

	public File file;

	public Config(File file) {
		this.file = file;

		try {
			load(file);
		} catch (FileNotFoundException ignored) {
		} catch (IOException | InvalidConfigurationException var4) {
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
		}
	}

	public Config(String configPath) {
		this(new File(configPath));
	}

	@Nonnull
	public MemorySection getMS(String path) {
		try {
			MemorySection memorySection = (MemorySection) this.get(path);
			if (memorySection != null) {
				return memorySection;
			}
			throw new CantReturnMemorySection();
		} catch (ClassCastException e) {
			throw new CantReturnMemorySection();
		}
	}

	@Nullable
	public MemorySection getMSOrNull(String path) {
		try {
			return getMS(path);
		} catch (CantReturnMemorySection e) {
			return null;
		}
	}

	public void setDefault(String path, Object value) {
		if (!contains(path)) set(path, value);
	}

	public long getLongValueOrZero(String path) {
		if (isLong(path)) {
			return getLong(path);
		} else if (isInt(path)) {
			return (long) getInt(path);
		} else {
			return 0L;
		}
	}

	public int getIntValueOrZero(String path) {
		if (isInt(path)) {
			return getInt(path);
		} else {
			return 0;
		}
	}

	public void save() {
		try {
			super.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Config loadConfiguration(String filePath) {
		File file = new File(filePath);
		return loadConfiguration(file);
	}

	public static Config loadConfiguration(File file) {
		Validate.notNull(file, "File cannot be null");
		return new Config(file);
	}

	public static Config getPlayerData(Player player) {
		return Config.loadConfiguration(PathManager.playerConfig(player.getName()));
	}

	public static class CantReturnMemorySection extends RuntimeException {}

}
