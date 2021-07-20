package application.controller;

import application.listeners.UIEventListener;
import application.listeners.modelListener;
import application.model.CompanyInterface;
import application.model.Department;
import application.model.Employee;
import application.model.Role;
import application.view.AbstractView;
import application.view.View;

public class CompanySimulationController implements modelListener, UIEventListener {
	private CompanyInterface Model;
	private AbstractView View;
	private int currentIndex;

	public CompanySimulationController(CompanyInterface model, View view) {
		this.Model = model;
		this.View = view;
		this.currentIndex = 0;
		Model.registerListener(this);
		View.registerListener(this);
	}

	@Override
	public void createAddDepartmentEvent(Department d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAddRoleEvent(Role r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAddEmployeeEvent(Employee e) {
		// TODO Auto-generated method stub

	}
}
