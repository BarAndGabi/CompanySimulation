package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import application.listeners.UIEventListener;
import application.listeners.modelListener;
import application.model.CompanyInterface;
import application.model.Department;
import application.model.Employee;
import application.model.Preference;
import application.model.PreferenceType;
import application.model.Role;
import application.model.cantFingObjectException;
import application.view.AbstractView;
import application.view.View;

public class CompanySimulationController implements modelListener, UIEventListener, Serializable {
	private CompanyInterface Model;
	private AbstractView View;

	public CompanySimulationController(CompanyInterface model, View view) throws Exception {
		try {
			this.Model = this.loadFileEvent();
		} catch (FileNotFoundException e) {
			this.Model = model;
		}
		this.View = view;
		Model.registerListener(this);
		View.registerListener(this);
		this.Model.addHardCoded();
	}

	public CompanyInterface loadFileEvent() throws Exception {
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Elections.dat"));
			CompanyInterface fileArchive = ((CompanyInterface) inFile.readObject());
			fileArchive.registerListener(this);
			inFile.close();
			return fileArchive;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new oldFilecCorruptedException();
		} catch (ClassNotFoundException e) {
			throw e;
		}
	}

	@Override
	public void createAddDepartmentEvent(Department d) {
		this.View.addDepartmentEvent(d.getName());
	}

	@Override
	public void createAddRoleEvent(Role r) {
		this.View.addRoleEvent(r.getjobTitle());

	}

	@Override
	public void createAddEmployeeEvent(Employee e) {
		this.View.addEmployeeEvent(e.getName());

	}

	@Override
	public void save() throws FileNotFoundException, IOException {
		this.Model.saveFile();
	}

	@Override
	public void addRoleToModel(double ProfitPerHour, String jobTitle, boolean sync, Department d, Preference preference,
			boolean workFromHome, boolean b) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDepartmentToModel(String name, boolean sync, boolean chooseP, PreferenceType p, int hourChange,
			boolean cP) throws Exception {
		this.Model.addDepartment(name, sync, chooseP, p, hourChange);
	}

	@Override
	public void addEmployeeHourlyToModel(String name, int yearOfBirth, Preference preference, int salaryPerHour,
			String jobTitle, boolean cP) throws Exception {
		Role r = this.findRole(jobTitle);
		this.Model.addEmployeeHourly(name, yearOfBirth, preference, salaryPerHour, r, cP);

	}

	private Role findRole(String jobTitle) throws cantFingObjectException {
		Role r = this.Model.findRole(jobTitle);
		return r;
	}

	@Override
	public void addEmployeeGlobalyToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception {
		Role r = this.findRole(jobTitle);
		this.Model.addEmployeeGlobaly(name, yearOfBirth, preference, salaryPerMonth, r, cP);
	}

	@Override
	public void addEmployeeGlobalyPlusToModel(String name, int yearOfBirth, Preference preference, int salaryPerMonth,
			String jobTitle, boolean cP) throws Exception {
		Role r = this.findRole(jobTitle);
		this.Model.addEmployeeGlobalyPlus(name, yearOfBirth, preference, salaryPerMonth, r, cP);
	}
}
