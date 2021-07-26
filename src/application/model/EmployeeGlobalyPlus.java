package application.model;

public class EmployeeGlobalyPlus extends EmployeeGlobaly {
	private double profitPrecent;

	public EmployeeGlobalyPlus(String name, int yearOfBirth, Preference preference, int salaryPerMonth, Role role,
			boolean cP) throws Exception {
		super(name, yearOfBirth, preference, salaryPerMonth, role, cP);
		this.profitPrecent = (int) (Math.random() * (100 - 0)) + 0;
	}

	@Override
	public String toString() {
		return super.toString() + "\nhour per month: " + this.profitPrecent;
	}
}
