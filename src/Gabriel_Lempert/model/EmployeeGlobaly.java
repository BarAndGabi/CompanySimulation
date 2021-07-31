package Gabriel_Lempert.model;

public class EmployeeGlobaly extends Employee {
	protected final int hourPerMonth = 160;
	protected int salaryPerMonth;

	public EmployeeGlobaly(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception {
		super(name, yearOfBirth, preference, role, cP);
		this.salaryPerMonth = salaryPerMonth;

	}

	@Override
	public String toString() {
		return super.toString() + "\nhour per month: " + this.hourPerMonth + "\nsalary per month: "
				+ this.salaryPerMonth;
	}
}
