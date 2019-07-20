package pl.saletrak.MostMajor.data_store;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @deprecated
 */
public class Data {

	private File configFile;
	private Config data;

	public Data(String path) {
		this.configFile = new File(path);
		this.data = Config.loadConfiguration(this.configFile);
//		if(this.configFile.exists() && !this.configFile.isDirectory()) {}
	}

	/**
	 * @throws CantReturnMemorySection e;
	 */
	public MemorySection getMemorySection(String path) {
		try {
			MemorySection memorySection = (MemorySection) this.getData().get(path);
			if (memorySection != null) {
				return memorySection;
			}
			throw new CantReturnMemorySection();
		} catch (ClassCastException e) {
			throw new CantReturnMemorySection();
		}
	}

	/**
	 * @deprecated
	 */
	public Object get(String key, Object def_value) {
		if(this.data != null) {
			Object value = this.data.get(key);
			if (value != null) return value;
		}
		return def_value;
	}

	/**
	 * @deprecated
	 */
	public void set(String key, Object value) {
		if(this.data != null) {
			this.data.set(key, value);
		}
	}

	public void save() {
		if(this.data != null) {
			try {
				this.data.save(this.configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Config getData() {
		return this.data;
	}

	/**
	 * @deprecated
	 */
	public boolean exist(String key) {
		return this.data.contains(key);
	}

	public static Data checkponitsData() {
		return new Data(PathManager.checkpointsConfig());
	}

	public static Data marketplaceData() {
		return new Data(PathManager.marketplaceConfig());
	}

	public static class CantReturnMemorySection extends RuntimeException {}
}
