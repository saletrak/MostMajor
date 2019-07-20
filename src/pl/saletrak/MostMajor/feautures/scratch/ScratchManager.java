package pl.saletrak.MostMajor.feautures.scratch;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.saletrak.MostMajor.player.MMPlayer;

import java.util.*;

public class ScratchManager {

	private Map<UUID, Scratch> scratchList = new HashMap<UUID, Scratch>();
	private Map<UUID, ScratchesListGui> scratchesGuiList = new HashMap<UUID, ScratchesListGui>();
	private Map<UUID, ScratchBuyGui> buyScratchGuiList = new HashMap<UUID, ScratchBuyGui>();
	private Map<Integer, ScratchGui> validInventories = new HashMap<Integer, ScratchGui>();

	public void addInventory(Inventory inv, ScratchGui sg) {
		validInventories.put(inv.hashCode(), sg);
	}

	public ScratchGui isValidInventory(Inventory inv) {
		if (validInventories.containsKey(inv.hashCode())) {
			return validInventories.get(inv.hashCode());
		}
		return null;
	}

	private Scratch newScratchForPlayer(Player player) {
		Scratch newScratch = new Scratch(player);
		this.scratchList.put(player.getUniqueId(), newScratch);
		return newScratch;
	}

	private ScratchesListGui newScratchesListGuiForPlayer(Player player) {
		MMPlayer mmPlayer = new MMPlayer(player);
		ScratchesListGui scratchesListGui = new ScratchesListGui(mmPlayer);
		this.scratchesGuiList.put(player.getUniqueId(), scratchesListGui);
		return scratchesListGui;
	}

	private ScratchBuyGui newBuyScratchGuiForPlayer(Player player) {
		MMPlayer mmPlayer = new MMPlayer(player);
		ScratchBuyGui scratchBuyGui = new ScratchBuyGui(mmPlayer);
		this.buyScratchGuiList.put(player.getUniqueId(), scratchBuyGui);
		return scratchBuyGui;
	}

	public Scratch getPlayerScratch(Player player, boolean allow_fired) {
		Scratch scratch = this.scratchList.get(player.getUniqueId());
		if(scratch == null) scratch = newScratchForPlayer(player);

		if(!allow_fired) if(scratch.fired) scratch = newScratchForPlayer(player);
		return scratch;
	}

	public Scratch getPlayerNewScratch(Player player) {
		return newScratchForPlayer(player);
	}

	public ScratchesListGui getPlayerScratchesListGui(Player player) {
		ScratchesListGui scratchesListGui = this.scratchesGuiList.get(player.getUniqueId());
		if (scratchesListGui == null) scratchesListGui = newScratchesListGuiForPlayer(player);
		return scratchesListGui;
	}

	public ScratchBuyGui getPlayerScratchBuyGui(Player player) {
		ScratchBuyGui scratchBuyGui = this.buyScratchGuiList.get(player.getUniqueId());
		if (scratchBuyGui == null) scratchBuyGui = newBuyScratchGuiForPlayer(player);
		return scratchBuyGui;
	}

}
