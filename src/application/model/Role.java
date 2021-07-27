package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Serializable, syncAble, choosePreference {

	private double profitPerHour;
	private String jobTitle;
	private ArrayList<Employee> Employees;
	private boolean sync;
	private boolean changePreference;
	private Department department;
	private Preference preference;
	private boolean workFromHome;
	protected double currentMoneyProfitForDay;
	protected double currentHourProfitForDay;

	public Role(double ProfitPerHour, String jobTitle, boolean sync, Department department, Preference preference,
			boolean workFromHome, boolean changePreference) {
		this.department = department;
		this.jobTitle = jobTitle;
		this.profitPerHour = ProfitPerHour;
		this.sync = sync;
		this.changePreference = changePreference;
		if (this.changePreference)
			this.preference = preference;
		else
			this.preference = new Preference(PreferenceType.REGULAR_START);
		this.workFromHome = workFromHome;
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		this.Employees = new ArrayList<Employee>();
	}

	public boolean addEmployee(Employee a) {
		if (!(this.Employees.contains(a))) {
			this.Employees.add(a);
			return true;
		}
		return false;
	}

	@Override
	public void choosePreference(PreferenceType t, int change) throws Exception {
		if (this.changePreference)
			this.preference = new Preference(t, change);
		else
			throw new cantChangePreferenceException();
	}

	@Override
	public boolean isSynced() {
		return this.sync;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Role))
			return false;
		Role temp = (Role) obj;
		if (temp.getjobTitle() == this.jobTitle)
			return true;
		else
			return false;
	}

	public String getjobTitle() {
		return this.jobTitle;
	}

	public Department getDepartment() {
		return this.department;
	}

	public double getProfitPerHour() {
		return this.profitPerHour;
	}

	@Override
	public boolean canChoosePreference() {
		return this.changePreference;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(this.getClass().getSimpleName() + " " + this.jobTitle + " info:" + "\n"
				+ "profit for hour: " + this.profitPerHour + "\nsyncronized role ? : " + this.sync
				+ "\ncan choose preference?  : " + this.changePreference + "\ncan work from home?  : "
				+ this.workFromHome + "\n preference : " + this.preference.toString() + "\ndeprtment :"
				+ this.department.getName() + "\nday's hours in value: " + this.currentHourProfitForDay
				+ "\nday's hours in money value: " + this.currentMoneyProfitForDay);
		str.append("\nthe employees in this role are: \n");
		for (int i = 0; i < this.Employees.size(); i++) {
			str.append(this.Employees.get(i).toString() + "\n---------------\n");
		}

		return str.toString();

	}

	public void calcProfit() {
		if (this.changePreference)
			if (this.sync)
				this.calcProfitWithPreference();
			else
				this.calcProfitNoPreference();
		else
			this.calcProfitNoPreference();

	}

	private void calcProfitNoPreference() {
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		for (int i = 0; i < this.Employees.size(); i++) {
			this.currentHourProfitForDay += this.Employees.get(i).calcHoursProfit();
			this.currentMoneyProfitForDay += this.Employees.get(i).calcMoueyProfit();
		}
	}

	private void calcProfitWithPreference() {
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		for (int i = 0; i < this.Employees.size(); i++) {
			this.currentHourProfitForDay += this.Employees.get(i).calcHoursProfit(this.preference);
			this.currentMoneyProfitForDay += this.Employees.get(i).calcMoueyProfit(this.preference);
		}
	}

	public void calcProfit(Preference p) {
		this.currentHourProfitForDay = 0;
		this.currentMoneyProfitForDay = 0;
		for (int i = 0; i < this.Employees.size(); i++) {
			this.currentHourProfitForDay += this.Employees.get(i).calcHoursProfit(p);
			this.currentMoneyProfitForDay += this.Employees.get(i).calcMoueyProfit(p);
		}
	}

	public String getSimulationResults() {
		StringBuffer str = new StringBuffer(this.jobTitle + ": \n" + "hour profit for day: "
				+ this.currentHourProfitForDay + "\nmoney profit for day: " + this.currentMoneyProfitForDay + "\nemployees: \n");
		for (int i = 0; i < this.Employees.size(); i++) {
			str.append((i + 1) + ") " + this.Employees.get(i).getSimulationResults());
		}
		return str.toString();
	}

	public boolean getWorkFromHome() {
		return this.workFromHome;
	}

}
