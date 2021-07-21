package application.model;

public class homeException extends Exception {
	public homeException() {
		super("can not choose HOME preferece with role that can not work from home");
	}
}
