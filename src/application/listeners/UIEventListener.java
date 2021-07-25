package application.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.model.Department;
import application.model.Preference;
import application.model.PreferenceType;
import application.model.Role;

public interface UIEventListener {
	void save() throws FileNotFoundException, IOException;

	Role addRoleToModel(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception;

	Department addDepartmentToModel(String name, boolean sync, PreferenceType p, int hourChange, boolean cP)
			throws Exception;

	void addEmployeeHourlyToModel(String name, int yearOfBirth, Preference preference, int salaryPerHour, Role role,
			boolean cP) throws Exception;

	void addEmployeeGlobalyToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception;

	void addEmployeeGlobalyPlusToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			Role role, boolean cP) throws Exception;

}
