package pl.saletrak.MostMajor.feautures.scratch.player_data;

import pl.saletrak.MostMajor.player.data.PlayerDataLogic;

import java.text.SimpleDateFormat;
import java.time.Instant;

public class ScratchPlayerDataLogic extends PlayerDataLogic<ScratchPlayerDataConfig> {

	private int basicScratches;
	private int premiumScratches;
	private long lastFreeDraft;

	public ScratchPlayerDataLogic(ScratchPlayerDataConfig config) {
		super(config);

		basicScratches = config.getAvaibleBasicScratchesStored();
		premiumScratches = config.getAvaiblePremiumScratchesStored();
		lastFreeDraft = config.getLastFreeDraftStored();
	}

	public int getBasicScratches() {
		return basicScratches;
	}

	public int getPremiumScratches() {
		return premiumScratches;
	}

	public void setBasicScratches(int basicScratches) {
		this.basicScratches = basicScratches;
	}

	public void setPremiumScratches(int premiumScratches) {
		this.premiumScratches = premiumScratches;
	}

	public void increaseBasicScratches(int amount) {
		basicScratches += amount;
	}

	public void increasePremiumScratches(int amount) {
		premiumScratches += amount;
	}

	public void decreaseBasicScratches(int amount) {
		basicScratches -= amount;
	}

	public void decreasePremiumScratches(int amount) {
		premiumScratches -= amount;
	}

	public void updateLastDraft() {
		lastFreeDraft = Instant.now().getEpochSecond();
	}

	public void setLastFreeDraft(long timestamp) {
		this.lastFreeDraft = timestamp;
	}

	public boolean isAvaibleFreeScratch() {
		Instant lastDraftDate = Instant.ofEpochSecond(lastFreeDraft);
		Instant timeNow = Instant.now();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat hourSdf = new SimpleDateFormat("HH");

		String lastDraftString = sdf.format(lastDraftDate.toEpochMilli());
		String timeNowString = sdf.format(timeNow.toEpochMilli());
		if (lastDraftString.equals(timeNowString)) {
			int lastDraftHour = Integer.valueOf(hourSdf.format(lastDraftDate.toEpochMilli()));
			int nowHour = Integer.valueOf(hourSdf.format(timeNow.toEpochMilli()));
			return lastDraftHour < 19 && nowHour >= 19;
		} else return true;
	}

	public void save() {
		getConfig().setAvaibleBasicScratchesStored(basicScratches);
		getConfig().setAvaiblePremiumScratchesStored(premiumScratches);
		getConfig().setLastFreeDraftStored(lastFreeDraft);
		getConfig().save();
	}
}
