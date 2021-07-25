package application.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.model.Department;
import application.model.Preference;
import application.model.PreferenceType;

public interface UIEventListener {
	void save() throws FileNotFoundException, IOException;

	void addRoleToModel(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception;

	void addDepartmentToModel(String name, boolean sync, boolean chooseP, PreferenceType p, int hourChange)
			throws Exception;

	void addEmployeeHourlyToModel(String name, int yearOfBirth, Preference preference, int salaryPerHour,
			String jobTitle, boolean cP) throws Exception;

	void addEmployeeGlobalyToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception;

	void addEmployeeGlobalyPlusToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception;

	String getEmployeesNames();

	String getRolesNames();

	String getDeparmentsNames();

}
