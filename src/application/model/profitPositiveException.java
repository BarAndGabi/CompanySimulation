package application.model;

public class profitPositiveException extends Exception {
	public profitPositiveException() {
		super(" need to be above zero");
	}
}
