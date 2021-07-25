package application.model;

public class profitPositiveException extends Exception {
	public profitPositiveException() {
		super("profit need to be above zero");
	}
}
