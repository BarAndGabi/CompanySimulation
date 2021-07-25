package application.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import application.listeners.modelListener;

public class Company implements Serializable, CompanyInterface {
	private ArrayList<Department> departments;
	protected double currentMoneyProfitForDay;
	protected double currentHourProfitForDay;
	private ArrayList<modelListener> listeners;
	private ArrayList<Role> roles;
	private ArrayList<Employee> employees;

	public Company() throws Exception {
		this.departments = new ArrayList<Department>();
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		this.listeners = new ArrayList<modelListener>();
		this.roles = new ArrayList<Role>();
		this.employees = new ArrayList<Employee>();

	}

	public Company(ArrayList<Department> departments2, ArrayList<Role> roles2, ArrayList<Employee> employees2,
			double currentHourProfitForDay2, double currentMoneyProfitForDay2) {
		this.roles = roles2;
		this.departments = departments2;
		this.employees = employees2;
		this.currentHourProfitForDay = currentHourProfitForDay2;
		this.currentMoneyProfitForDay = currentMoneyProfitForDay2;
		this.listeners = new ArrayList<modelListener>();
	}

	@Override
	public void addHardCoded() throws Exception {
		Preference p1 = new Preference(PreferenceType.REGULAR_START, 0);
		Preference p2 = new Preference(PreferenceType.LATE_START, 2);
		Preference p3 = new Preference(PreferenceType.EARLY_START, 3);
		Preference p4 = new Preference(PreferenceType.HOME, 0);
		Department logistic = this.addDepartment("logistic", false, true, PreferenceType.REGULAR_START, 0);
		Department cars = this.addDepartment("cars", false, false, PreferenceType.EARLY_START, 6);
		Department mangment = this.addDepartment("mangment", true, true, PreferenceType.EARLY_START, 3);
		Department wizards = this.addDepartment("wizards", false, true, PreferenceType.LATE_START, 3);
		Role cleaner = this.addRole(35.5, "cleaner", true, logistic, p2, true, true);
		Role carSalesMan = this.addRole(34, "car sales man", true, cars, p3, true, true);
		Role fileOrganizer = this.addRole(25, "file organizer", false, mangment, p2, true, true);
		Role harryPotter = this.addRole(60, "harryPotter", false, wizards, p4, true, true);
		EmployeeGlobaly yossi = new EmployeeGlobaly("yossi", 2002, p3, 7500, fileOrganizer, true);
		EmployeeGlobaly bar = new EmployeeGlobaly("bar", 1996, p1, 11000, cleaner, true);
		EmployeeGlobalyPlus itay = new EmployeeGlobalyPlus("itay", 2000, p1, 23000, harryPotter, false);
		EmployeeGlobalyPlus ofir = new EmployeeGlobalyPlus("ofir", 1983, p4, 16000, fileOrganizer, true);
		EmployeeHourly mor = new EmployeeHourly("mor", 2002, p2, 55, carSalesMan, true);
		EmployeeHourly yotam = new EmployeeHourly("yotam", 2002, p2, 31, harryPotter, false);
		this.addEmployeeToDepartment(yossi);
		this.addEmployeeToDepartment(bar);
		this.addEmployeeToDepartment(itay);
		this.addEmployeeToDepartment(ofir);
		this.addEmployeeToDepartment(mor);
		this.addEmployeeToDepartment(yotam);

	}

	@Override
	public void addEmployeeHourly(String name, int yearOfBirth, Preference preference, int salaryPerHour, Role role,
			boolean cP) throws Exception {
		this.addEmployeeToDepartment(new EmployeeHourly(name, yearOfBirth, preference, salaryPerHour, role, cP));
	}

	@Override
	public void addEmployeeGlobaly(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception {
		this.addEmployeeToDepartment(new EmployeeGlobaly(name, yearOfBirth, preference, salaryPerMonth, role, cP));

	}

	@Override
	public void addEmployeeGlobalyPlus(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			Role role, boolean cP) throws Exception {

		this.addEmployeeToDepartment(new EmployeeGlobalyPlus(name, yearOfBirth, preference, salaryPerMonth, role, cP));
	}

	public void addEmployeeToDepartment(Employee a) {
		int index = this.findDepartment(a.getDepartment());
		this.departments.get(index).addEmployee(a);
		this.employees.add(a);
		this.fireAddEmployeeEvent(a);
		this.runSimulation();

	}

	private void fireAddEmployeeEvent(Employee e) {
		for (modelListener m : this.listeners) {
			m.createAddEmployeeEvent(e);
		}
	}

	private int findDepartment(Department d) {
		return this.departments.indexOf(d);

	}

	@Override
	public Department addDepartment(String name, boolean sync, boolean chooseP, PreferenceType p, int hourChange)
			throws Exception {
		if (this.departmentNotExist(name)) {
			Department d = new Department(name, sync, p, hourChange, chooseP);
			for (int i = 0; i < this.departments.size(); i++) {
				if (this.departments.get(i).equals(d))
					throw new alreadyExistException();
			}
			this.departments.add(d);
			this.fireAddDepartmentEvent(d);
			this.runSimulation();
			return d;
		} else
			throw new alreadyExistException();
	}

	private boolean departmentNotExist(String name) {
		for (int i = 0; i < this.departments.size(); i++) {
			if (this.departments.get(i).getName().equals(name))
				return false;
		}
		return true;
	}

	private void fireAddDepartmentEvent(Department d) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).createAddDepartmentEvent(d);
		}
	}

	@Override
	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception {
		if (this.roleNotExist(jobTitle)) {
			if (ProfitPerHour >= 0) {
				if ((preference.getPreferenceType() == PreferenceType.HOME) && (!workFromHome))
					throw new homeException();
				else {
					Role r = new Role(ProfitPerHour, jobTitle, sync, d, preference, workFromHome, b);
					int index = this.findDepartment(d);
					this.departments.get(index).addRole(r);
					this.roles.add(r);
					this.fireAddRoleEvent(r);
					this.runSimulation();
					return r;
				}
			} else
				throw new profitPositiveException();
		} else
			throw new alreadyExistException();
	}

	private boolean roleNotExist(String jobTitle) {
		for (int i = 0; i < this.departments.size(); i++) {
			if (this.departments.get(i).getName().equals(jobTitle))
				return false;
		}
		return true;
	}

	private void fireAddRoleEvent(Role r) {
		for (modelListener m : this.listeners) {
			m.createAddRoleEvent(r);
		}
	}

	@Override
	public void runSimulation() {
		for (int i = 0; i < this.departments.size(); i++) {
			this.currentHourProfitForDay = 0;
			this.currentMoneyProfitForDay = 0;
			this.departments.get(i).calcProfit();
			this.currentHourProfitForDay += this.departments.get(i).getCurrentHourProfitForDay();
			this.currentMoneyProfitForDay += this.departments.get(i).getCurrentMoneyProfitForDay();
		}
	}

	@Override
	public String toString() {
		this.runSimulation();
		StringBuffer str = new StringBuffer(this.getClass().getSimpleName() + "info : \n" + "hour profit for day: "
				+ this.currentHourProfitForDay + "\nmoney profit for day: " + this.currentMoneyProfitForDay + "\n\n");
		for (int i = 0; i < this.departments.size(); i++) {
			str.append("########-" + (i + 1) + "-########\n" + this.departments.get(i).toString());
		}
		return str.toString();
	}

	@Override
	public void registerListener(modelListener x) {
		this.listeners.add(x);
	}

	@Override
	public String getSimulationResults() {
		this.runSimulation();

		StringBuffer str = new StringBuffer(this.getClass().getSimpleName() + "simulation results: \n"
				+ "hour profit for day: " + this.currentHourProfitForDay + "\nmoney profit for day: "
				+ this.currentMoneyProfitForDay + "\n");
		for (int i = 0; i < this.departments.size(); i++) {
			str.append("########-" + (i + 1) + "-########\n" + this.departments.get(i).getSimulationResults());
		}
		return str.toString();
	}

	@Override
	public void saveFile() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Company_Simulation.dat"));
		outFile.writeObject(new Company(this.departments, this.roles, this.employees, this.currentHourProfitForDay,
				this.currentMoneyProfitForDay));
		;
		outFile.close();
	}

	@Override
	public void changePrefernce(String name, objectType o, PreferenceType p, int hourChange) throws Exception {
		switch (o) {
		case DEPARTMENT:
			for (int i = 0; i < this.departments.size(); i++) {
				if (this.departments.get(i).getName().equals(name)) {
					this.departments.get(i).choosePreference(p, hourChange);
					break;
				}
				throw new cantFingObjectException();

			}
		case ROLE:
			for (int i = 0; i < this.departments.size(); i++) {
				if (this.roles.get(i).getjobTitle().equals(name)) {
					this.roles.get(i).choosePreference(p, hourChange);
					break;
				}
				throw new cantFingObjectException();
			}
		case EMPLOYEE:
			for (int i = 0; i < this.departments.size(); i++) {
				if (this.employees.get(i).getName().equals(name)) {
					this.employees.get(i).choosePreference(p, hourChange);
					break;
				}
				throw new cantFingObjectException();
			}

		}

	}

	@Override
	public Role findRole(String jobTitle) throws cantFingObjectException {
		for (int i = 0; i < this.roles.size(); i++)
			if (jobTitle.equals(this.roles.get(i).getjobTitle()))
				return this.roles.get(i);
		throw new cantFingObjectException();
	}

	@Override
	public ArrayList<String> getEmployeesNames(ArrayList<String> temp) {
		for (int i = 0; i < this.employees.size(); i++)
			temp.add(this.employees.get(i).name);
		return temp;

	}

	@Override
	public ArrayList<String> getRolesNames(ArrayList<String> temp) {
		for (int i = 0; i < this.roles.size(); i++)
			temp.add(this.roles.get(i).getjobTitle());
		return temp;

	}

	@Override
	public ArrayList<String> getDepartmentNames(ArrayList<String> temp) {
		for (int i = 0; i < this.departments.size(); i++)
			temp.add(this.departments.get(i).getName());
		return temp;

	}

	@Override
	public Department findDepartment(String name) throws cantFingObjectException {
		for (int i = 0; i < this.departments.size(); i++)
			if (name.equals(this.departments.get(i).getName()))
				return this.departments.get(i);
		throw new cantFingObjectException();
	}

}