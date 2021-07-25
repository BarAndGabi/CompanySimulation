package application.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import application.listeners.modelListener;

public interface CompanyInterface {

	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception;

	Department addDepartment(String name, boolean sync, boolean chooseP, PreferenceType p, int hourChange)
			throws Exception;

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

	void addHardCoded() throws Exception;

	void runSimulation();

	public Role findRole(String jobTitle) throws cantFingObjectException;

	ArrayList<String> getEmployeesNames(ArrayList<String> temp);

	ArrayList<String> getRolesNames(ArrayList<String> temp);

	ArrayList<String> getDepartmentNames(ArrayList<String> temp);

	Department findDepartment(String name) throws cantFingObjectException;
}