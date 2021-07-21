package application.model;

import java.io.Serializable;

public class Employee extends Person implements Serializable, choosePreference {
	protected int employeeId;
	protected static int idSerialCounter = 1000;
	protected Preference preference;
	protected Role role;
	protected boolean changePreference;
	protected double currentMoneyProfitForDay;
	protected double currentHourProfitForDay;

	public Employee(String name, int yearOfBirth, Preference preference, Role role, boolean changePreference)
			throws Exception {
		super(name, yearOfBirth);
		this.employeeId = idSerialCounter++;
		this.preference = preference;
		this.role = role;
		this.changePreference = changePreference;
		this.currentHourProfitForDay = this.calcHoursProfit();
		this.currentMoneyProfitForDay = this.calcMoueyProfit();

	}

	@Override
	public void choosePreference(PreferenceType t, int change) {
		this.preference = new Preference(t, change);

	}

	public Department getDepartment() {
		return this.role.getDepartment();

	}

	private int getEmployeeId() {
		return this.employeeId;
	}

	public Role getRole() {
		return this.role;
	}

	public double calcHoursProfit() {
		double profit = 0;
		if (this.canChoosePreference()) {
			int currentHourChange = 0;
			switch (preference.getPreferenceType()) {
			case EARLY_START:
				currentHourChange = this.preference.getHourChange();
				profit = (1.2 * currentHourChange) + (1 * (8 - currentHourChange));
				break;
			case LATE_START:
				currentHourChange = this.preference.getHourChange();
				profit = (1.2 * currentHourChange) + (1 * (8 - currentHourChange));
				break;
			case HOME:
				if (this.role.getWorkFromHome())
					profit = 1.1 * 8;
				else
					profit = 8;
				break;
			case REGULAR_START:
				profit = 8;
				break;

			}
		} else
			profit = 8;
		this.currentHourProfitForDay = profit;
		this.currentMoneyProfitForDay = this.currentHourProfitForDay * this.role.getProfitPerHour();
		return profit;
	}

	public double calcMoueyProfit() {
		double currentHourProfit = this.role.getProfitPerHour();
		return this.calcHoursProfit() * currentHourProfit;
	}

	public double calcMoueyProfit(Preference p) {
		double currentHourProfit = this.role.getProfitPerHour();
		return this.calcHoursProfit(p) * currentHourProfit;
	}

	public double calcHoursProfit(Preference p) {
		double profit = 0;
		if (this.canChoosePreference()) {
			if (p.getPreferenceType() == PreferenceType.HOME) {
				if (this.preference.getPreferenceType() == PreferenceType.HOME)
					if (this.role.getWorkFromHome())
						profit = 8 * 1.1;
					else
						profit = 8;
				else
					profit = 8 * 1;
			} else {
				if (this.preference.getPreferenceType() == PreferenceType.HOME) {
					profit = 8;
				} else {
					int employeeEnd, employeeStart, end, start;
					start = p.getStartHour();
					end = start + 9;
					employeeStart = this.preference.getStartHour();
					employeeEnd = employeeStart + 9;
					int randomLunchBreak = (int) (Math.random() * (end - start)) + start;
					for (int currentHour = start; currentHour < end; currentHour++) {
						if (currentHour != randomLunchBreak) {
							if (currentHour < 8) {
								if (employeeStart <= currentHour)
									profit += 1.2;
								else
									profit += 0.8;

							} else if (currentHour >= 8 && currentHour < 17) {
								profit++;

							} else if (currentHour > 17) {
								if (employeeEnd <= currentHour)
									profit += 0.8;
								else
									profit += 1.2;
							}
						}
					}
				}
			}
		} else
			profit = 8;
		this.currentHourProfitForDay = profit;
		this.currentMoneyProfitForDay = this.currentHourProfitForDay * this.role.getProfitPerHour();
		return profit;
	}

	public String returnStringResult(Preference p) {
		double moneyProfit;
		double hourProfit;
		if (p == null) {
			moneyProfit = this.calcMoueyProfit();
			hourProfit = this.calcHoursProfit();

		} else {
			moneyProfit = this.calcMoueyProfit(p);
			hourProfit = this.calcHoursProfit(p);
		}
		return this.name + " values for day:\n money: " + moneyProfit + "\nhours: " + hourProfit;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Employee))
			return false;
		Employee temp = (Employee) obj;
		if (temp.getEmployeeId() == this.employeeId)
			return true;
		else
			return false;
	}

	@Override
	public boolean canChoosePreference() {
		return this.changePreference;
	}

	@Override
	public String toString() {
		return (this.name + ":" + "\nid: " + this.employeeId + "\npreference :" + this.preference.toString()
				+ "\nrole: " + this.role.getjobTitle() + "\ncan choose preference? : " + this.changePreference
				+ "\nday's hours in value: " + this.currentHourProfitForDay + "\nday's hours in money value: "
				+ this.currentMoneyProfitForDay);
	}

	public String getSimulationResults() {
		StringBuffer str = new StringBuffer(this.name + ": \n" + "hour profit for day: " + this.currentHourProfitForDay
				+ "\nmoney profit for day: " + this.currentMoneyProfitForDay + "\n");
		return str.toString();
	}

	public String getName() {
		return this.name;
	}

}
