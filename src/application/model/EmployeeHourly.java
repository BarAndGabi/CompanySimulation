package application.model;

public class EmployeeHourly extends Employee {
	private int hourPerMonth;
	private int salaryPerHour;

	public EmployeeHourly(String name, int yearOfBirth, Preference preference, int salaryPerHour, Role role, boolean cP)
			throws Exception {
		super(name, yearOfBirth, preference, role, cP);
		this.salaryPerHour = salaryPerHour;

	}

	@Override
	public String toString() {
		return super.toString() + "\nhour per month: " + this.hourPerMonth + "\nsalary per month: "
				+ this.salaryPerHour;
	}

}
