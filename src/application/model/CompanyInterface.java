package application.model;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.listeners.modelListener;

public interface CompanyInterface {

	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception;

	void runSimulation();

	Department addDepartment(String name, boolean sync, PreferenceType p, int hourChange, boolean cP) throws Exception;

	void addEmployeeHourly(String name, int yearOfBirth, Preference preference, int salaryPerHour, Role role,
			boolean cP) throws Exception;

	void addEmployeeGlobaly(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception;

	void addEmployeeGlobalyPlus(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception;

	void registerListener(modelListener x);

	String getSimulationResults();

	void saveFile() throws FileNotFoundException, IOException;

	void changePrefernce(String name, objectType o, PreferenceType p, int hourChange) throws Exception;

}