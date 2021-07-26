package application.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import application.model.Preference;
import application.model.PreferenceType;
import application.model.objectType;

public interface UIEventListener {
	void save() throws FileNotFoundException, IOException;

	void addRoleToModel(double ProfitPerHour, String jobTitle, boolean sync, String department, Preference preference,
			boolean workFromHome, boolean b) throws Exception;

	void addDepartmentToModel(String name, boolean sync, boolean chooseP, PreferenceType p, int hourChange)
			throws Exception;

	void addEmployeeHourlyToModel(String name, int yearOfBirth, Preference preference, int salaryPerHour,
			String jobTitle, boolean cP) throws Exception;

	void addEmployeeGlobalyToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception;

	void addEmployeeGlobalyPlusToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception;

	ArrayList<String> getEmployeesNames();

	ArrayList<String> getRolesNames();

	ArrayList<String> getDeparmentsNames();

	void choosePreference(PreferenceType p, int hourChange, objectType o, String name) throws Exception;

	String getObjectToString(objectType o, String name) throws Exception;

}
