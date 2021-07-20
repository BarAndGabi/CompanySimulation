package application.model;

import java.io.Serializable;

public abstract class Person implements Serializable {
	protected String name;
	protected int yearOfBirth;

	public Person(String name, int yearOfBirth) throws Exception {
		setName(name);
		setYearOfbirth(yearOfBirth);
	}

	public boolean setYearOfbirth(int year) throws Exception {
		if (year >= 1900 && year < 2100) {
			this.yearOfBirth = year;
		} else
			throw new Exception("year must be between 1900-2100");
		return true;
	}

	public boolean setName(String name) throws Exception {

		this.name = name;
		return true;
	}

}
