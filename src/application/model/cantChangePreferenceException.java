package application.model;

public class cantChangePreferenceException extends Exception {
	public cantChangePreferenceException() {
		super("cannot change prefertions to this object");
	}
}
