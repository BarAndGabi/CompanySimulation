package Gabriel_Lempert.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Gabriel_Lempert.listeners.modelListener;

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
		Preference p2 = new Preference(PreferenceType.LATE_START, 6);
		Preference p3 = new Preference(PreferenceType.EARLY_START, 3);
		Preference p4 = new Preference(PreferenceType.HOME, 0);
		Department logistic = this.addDepartment("Logistic", false, true, PreferenceType.REGULAR_START, 0);
		Department cars = this.addDepartment("Cars", false, false, PreferenceType.EARLY_START, 6);
		Department Hogwars = this.addDepartment("Hogwars", true, true, PreferenceType.EARLY_START, 2);
		Department Ninjas = this.addDepartment("Ninjas", false, true, PreferenceType.LATE_START, 7);
		Role cleaner = this.addRole(35.5, "Cleaner", true, logistic, p2, true, true);
		Role Princess = this.addRole(34, "Pirncess", true, cars, p3, true, true);
		Role fileOrganizer = this.addRole(25, "file organizer", false, Hogwars, p2, true, true);
		Role harryPotter = this.addRole(60, "harryPotter", false, Ninjas, p4, true, true);
		EmployeeGlobaly galya = new EmployeeGlobaly("galya", 2002, p3, 7500, fileOrganizer, true);
		EmployeeGlobaly bar = new EmployeeGlobaly("bar", 1996, p1, 11000, cleaner, true);
		EmployeeGlobalyPlus itay = new EmployeeGlobalyPlus("itay", 2000, p1, 23000, harryPotter, false);
		EmployeeGlobalyPlus ofir = new EmployeeGlobalyPlus("ofir", 1983, p4, 16000, fileOrganizer, true);
		EmployeeHourly shani = new EmployeeHourly("shani", 2002, p2, 55, Princess, true);
		EmployeeHourly ella = new EmployeeHourly("ellas", 2002, p2, 31, harryPotter, false);
		this.addEmployeeToDepartment(galya);
		this.addEmployeeToDepartment(bar);
		this.addEmployeeToDepartment(itay);
		this.addEmployeeToDepartment(ofir);
		this.addEmployeeToDepartment(shani);
		this.addEmployeeToDepartment(ella);

	}

	@Override
	public void addEmployeeHourly(String name, int yearOfBirth, Preference preference, int salaryPerHour, Role role,
			boolean cP) throws Exception {
		if (salaryPerHour >= 0) {
			this.addEmployeeToDepartment(new EmployeeHourly(name, yearOfBirth, preference, salaryPerHour, role, cP));
		} else {
			throw new positiveException();
		}
	}

	@Override
	public void addEmployeeGlobaly(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception {
		if (salaryPerMonth >= 0) {
			this.addEmployeeToDepartment(new EmployeeGlobaly(name, yearOfBirth, preference, salaryPerMonth, role, cP));
		} else {
			throw new positiveException();
		}
	}

	@Override
	public void addEmployeeGlobalyPlus(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			Role role, boolean cP) throws Exception {
		if (salaryPerMonth >= 0) {
			this.addEmployeeToDepartment(
					new EmployeeGlobalyPlus(name, yearOfBirth, preference, salaryPerMonth, role, cP));
		} else {
			throw new positiveException();
		}

	}

	public void addEmployeeToDepartment(Employee a) throws alreadyExistException {
		if (this.employeeNotExist(a.getName())) {
			int index = this.findDepartment(a.getDepartment());
			this.departments.get(index).addEmployee(a);
			this.employees.add(a);
			this.fireAddEmployeeEvent(a);
			this.runSimulation();
		} else {
			throw new alreadyExistException();
		}

	}

	private boolean employeeNotExist(String name) {
		for (Employee element : this.employees) {
			if (element.getName().equals(name)) {
				return false;
			}
		}
		return true;
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
			for (Department element : this.departments) {
				if (element.equals(d)) {
					throw new alreadyExistException();
				}
			}
			this.departments.add(d);
			this.fireAddDepartmentEvent(d);
			this.runSimulation();
			return d;
		} else {
			throw new alreadyExistException();
		}
	}

	private boolean departmentNotExist(String name) {
		for (Department element : this.departments) {
			if (element.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	private void fireAddDepartmentEvent(Department d) {
		for (modelListener element : this.listeners) {
			element.createAddDepartmentEvent(d);
		}
	}

	@Override
	public Role addRole(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception {
		if (this.roleNotExist(jobTitle)) {
			if (ProfitPerHour >= 0) {
				if ((preference.getPreferenceType() == PreferenceType.HOME) && (!workFromHome)) {
					throw new homeException();
				} else {
					Role r = new Role(ProfitPerHour, jobTitle, sync, d, preference, workFromHome, b);
					int index = this.findDepartment(d);
					this.departments.get(index).addRole(r);
					this.roles.add(r);
					this.fireAddRoleEvent(r);
					this.runSimulation();
					return r;
				}
			} else {
				throw new positiveException();
			}
		} else {
			throw new alreadyExistException();
		}
	}

	private boolean roleNotExist(String jobTitle) {
		for (Department element : this.departments) {
			if (element.getName().equals(jobTitle)) {
				return false;
			}
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
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		for (Department element : this.departments) {
			element.calcProfit();
			this.currentHourProfitForDay += element.getCurrentHourProfitForDay();
			this.currentMoneyProfitForDay += element.getCurrentMoneyProfitForDay();
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
				+ this.currentMoneyProfitForDay + "\ndepratments:\n");
		for (Department element : this.departments) {
			str.append( element.getSimulationResults());
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
			for (Department element : this.departments) {
				if (element.getName().equals(name)) {
					element.choosePreference(p, hourChange);
					break;
				}

			}

		case ROLE:
			for (Role element : this.roles) {
				if (element.getjobTitle().equals(name)) {
					element.choosePreference(p, hourChange);
					break;
				}
			}

		case EMPLOYEE:
			for (Employee element : this.employees) {
				if (element.getName().equals(name)) {
					element.choosePreference(p, hourChange);
					break;
				}

			}

		}

	}

	@Override
	public Role findRole(String jobTitle) throws cantFingObjectException {
		for (Role element : this.roles) {
			if (jobTitle.equals(element.getjobTitle())) {
				return element;
			}
		}
		throw new cantFingObjectException();
	}

	@Override
	public ArrayList<String> getEmployeesNames(ArrayList<String> temp) {
		for (int i = 0; i < this.employees.size(); i++) {
			temp.add(this.employees.get(i).name);
		}
		return temp;

	}

	@Override
	public ArrayList<String> getRolesNames(ArrayList<String> temp) {
		for (int i = 0; i < this.roles.size(); i++) {
			temp.add(this.roles.get(i).getjobTitle());
		}
		return temp;

	}

	@Override
	public ArrayList<String> getDepartmentNames(ArrayList<String> temp) {
		for (int i = 0; i < this.departments.size(); i++) {
			temp.add(this.departments.get(i).getName());
		}
		return temp;

	}

	@Override
	public Department findDepartment(String name) throws cantFingObjectException {
		for (Department element : this.departments) {
			if (name.equals(element.getName())) {
				return element;
			}
		}
		throw new cantFingObjectException();
	}

	@Override
	public String getToString(objectType o, String name) throws Exception {
		switch (o) {
		case DEPARTMENT:
			for (Department element : this.departments) {
				if (element.getName().equals(name)) {
					return element.toString();

				}

			}
		case ROLE:
			for (Role element : this.roles) {
				if (element.getjobTitle().equals(name)) {
					return element.toString();
				}
			}
		case EMPLOYEE:
			for (Employee element : this.employees) {
				if (element.getName().equals(name)) {
					return element.toString();
				}
			}
		}
		return null;

	}

	@Override
	public String getSimulationResult(objectType o, String name) throws Exception {
		this.runSimulation();
		switch (o) {
		case DEPARTMENT:
			for (Department element : this.departments) {
				if (element.getName().equals(name)) {
					return element.getSimulationResults();
				}

			}
		case ROLE:
			for (Role element : this.roles) {
				if (element.getjobTitle().equals(name)) {
					return element.getSimulationResults();
				}
			}
		case EMPLOYEE:
			for (Employee element : this.employees) {
				if (element.getName().equals(name)) {
					return element.getSimulationResults();
				}
			}
		}
		return null;
	}

}