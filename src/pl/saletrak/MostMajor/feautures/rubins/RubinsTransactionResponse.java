package pl.saletrak.MostMajor.feautures.rubins;

public enum RubinsTransactionResponse {

	SUCCESS(true),
	NO_GIVEN_PLAYER(false),
	INSUFFICIENT_FUNDS(false);

	boolean success;
	RubinsTransactionResponse(boolean success) {
		this.success = success;
	}

}
