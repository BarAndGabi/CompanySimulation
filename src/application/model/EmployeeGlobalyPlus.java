package application.model;

public class EmployeeGlobalyPlus extends EmployeeGlobaly {
	private double partOfMonthSales;

	public EmployeeGlobalyPlus(String name, int yearOfBirth, Preference preference, Role role, int salaryPerMonth,
			boolean cP) throws Exception {
		super(name, yearOfBirth, preference, salaryPerMonth, role, cP);

	}

}
