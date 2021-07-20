package application.model;

import java.util.ArrayList;

public class Company implements CompanyInterface {
	private ArrayList<Department> departments;
	private ArrayList<Simulation> SimulationsArchive;
	protected double currentMoneyProfitForDay;
	protected double currentHourProfitForDay;

	public Company() throws Exception {
		this.SimulationsArchive = new ArrayList<Simulation>();
		this.departments = new ArrayList<Department>();
		this.addHardCoded();
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
	}

	private void addHardCoded() throws Exception {
		Preference p1 = new Preference(PreferenceType.REGULAR_START, 0);
		Preference p2 = new Preference(PreferenceType.LATE_START, 2);
		Preference p3 = new Preference(PreferenceType.EARLY_START, 8);
		Preference p4 = new Preference(PreferenceType.HOME, 0);
		Department logistic = this.addDepartment("logistic", false, PreferenceType.REGULAR_START, 0, false);
		Department cars = this.addDepartment("cars", false, PreferenceType.EARLY_START, 6, true);
		Department mangment = this.addDepartment("mangment", true, PreferenceType.REGULAR_START, 0, true);
		Department wizards = this.addDepartment("wizards", false, PreferenceType.LATE_START, 3, true);
		Role cleaner = this.addRole(35.5, "cleaner", true, logistic, p2, true, true);
		Role carSalesMan = this.addRole(34, "car sales man", true, cars, p3, true, true);
		Role fileOrganizer = this.addRole(25, "file organizer", true, mangment, p2, false, true);
		Role harryPotter = this.addRole(450.3, "harryPotter", false, wizards, p4, true, true);
		EmployeeGlobaly yossi = new EmployeeGlobaly("yossi", 2002, p3, 7500, fileOrganizer, true);
		EmployeeGlobaly bar = new EmployeeGlobaly("bar", 1996, p1, 11000, cleaner, true);
		EmployeeGlobalyPlus itay = new EmployeeGlobalyPlus("itay", 2000, p1, harryPotter, 23000, false);
		EmployeeGlobalyPlus ofir = new EmployeeGlobalyPlus("ofir", 1983, p4, fileOrganizer, 16000, true);
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

	public void addEmployeeToDepartment(Employee a) {
		int index = this.findDepartment(a.getDepartment());
		this.departments.get(index).addEmployee(a);
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
		return d;
	}

	@Override
	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception {
		Role r = new Role(ProfitPerHour, jobTitle, sync, d, preference, workFromHome, b);
		int index = this.findDepartment(d);
		this.departments.get(index).addRole(r);
		return r;

	}

	@Override
	public void runSimulation() {
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		return str.toString();
	}

}
