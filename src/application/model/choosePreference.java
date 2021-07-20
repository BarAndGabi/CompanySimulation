package application.model;

public interface choosePreference {
	void choosePreference(PreferenceType t, int change) throws Exception;

	boolean canChoosePreference();
}
