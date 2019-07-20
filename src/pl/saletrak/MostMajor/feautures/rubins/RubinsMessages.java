package pl.saletrak.MostMajor.feautures.rubins;

import de.Herbystar.TTA.TTA_Methods;
import org.bukkit.entity.Player;

public class RubinsMessages {

	private Player p;

	public RubinsMessages(Player p) {
		this.p = p;
	}

	public void noPermission() {
		p.sendMessage("You are not permitted to do this");
	}

	public void playerRubinsAmountChat(long amount) {
		p.sendMessage("Twoje rubiny: " + printAmount(amount));
	}

	public void otherRubinsAmount(String playerName, long amount) {
		p.sendMessage("Rubiny gracza " + playerName + ": " + printAmount(amount));
	}

	public void playerRubinsAmountTitle(long amount) {
		int stay = 45;
		int fade = 10;
		TTA_Methods.sendTitle(p, printAmount(amount), fade, stay, fade, "Twoje Rubiny", fade, stay, fade);
	}

	public void successPayment() {
		p.sendMessage("Pomyślnie przesłano pieniądze");
	}

	public void givenPlayerNotFound() {
		p.sendMessage("Nie znaleziono podanego gracza");
	}

	public void insufficentFunds() {
		p.sendMessage("Nie masz wystarczająco rubinów");
	}

	public void invalidAmountValue() {
		p.sendMessage("Podana kwota nie jest poprawna");
	}

	public void dataError() {
		p.sendMessage("Nie udało się pobrać danych gracza");
	}

	public void increaseRubins(long amount) {
		p.sendMessage("Do twojego konta dodano " + printAmount(amount));
	}

	public void confirmTransferToReceiver(Player reciver, long amount) {
		reciver.sendMessage(("Otrzymałeś " + printAmount(amount) + " od " + p.getDisplayName()));
	}

	public static String printAmount(long amount) {
		return "§4" + amount + "§l§oR§r";
	}

}
