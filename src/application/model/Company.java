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
	private ArrayList<Simulation> SimulationsArchive;
	protected double currentMoneyProfitForDay;
	protected double currentHourProfitForDay;
	private ArrayList<modelListener> listeners;

	public Company() throws Exception {
		this.SimulationsArchive = new ArrayList<Simulation>();
		this.departments = new ArrayList<Department>();
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		this.listeners = new ArrayList<modelListener>();
		this.addHardCoded();

	}

	private void addHardCoded() throws Exception {
		Preference p1 = new Preference(PreferenceType.REGULAR_START, 0);
		Preference p2 = new Preference(PreferenceType.LATE_START, 2);
		Preference p3 = new Preference(PreferenceType.EARLY_START, 3);
		Preference p4 = new Preference(PreferenceType.HOME, 0);
		Department logistic = this.addDepartment("logistic", false, PreferenceType.REGULAR_START, 0, false);
		Department cars = this.addDepartment("cars", false, PreferenceType.EARLY_START, 6, true);
		Department mangment = this.addDepartment("mangment", true, PreferenceType.EARLY_START, 3, true);
		Department wizards = this.addDepartment("wizards", false, PreferenceType.LATE_START, 3, true);
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
	public Department addDepartment(String name, boolean sync, PreferenceType p, int hourChange, boolean cP)
			throws Exception {
		Department d = new Department(name, sync, p, hourChange, cP);
		for (int i = 0; i < this.departments.size(); i++) {
			if (this.departments.get(i).equals(d))
				throw new alreadyExistException();
		}
		this.departments.add(d);
		this.fireAddDepartmentEvent(d);
		this.runSimulation();
		return d;
	}

	private void fireAddDepartmentEvent(Department d) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).createAddDepartmentEvent(d);
		}
	}

	@Override
	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception {
		if ((preference.getPreferenceType() == PreferenceType.HOME) && (!workFromHome))
			throw new homeException();
		else {
			Role r = new Role(ProfitPerHour, jobTitle, sync, d, preference, workFromHome, b);
			int index = this.findDepartment(d);
			this.departments.get(index).addRole(r);
			this.fireAddRoleEvent(r);
			this.runSimulation();
			return r;
		}
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
		this.SimulationsArchive.add(new Simulation(str.toString()));
		return str.toString();
	}

	@Override
	public void saveFile() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Company_Simulation.dat"));
		outFile.writeObject(this);
		outFile.close();
	}

}
