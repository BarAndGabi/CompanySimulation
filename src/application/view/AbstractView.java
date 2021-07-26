package application.view;

import application.listeners.UIEventListener;

public interface AbstractView {

	void registerListener(UIEventListener companySimulationController);

	void addEmployeeEvent(String name);// add to employees combobox

	void addRoleEvent(String jobTitle);// add to roles combobox

	void addDepartmentEvent(String name);// add to departments combobox

	void addEmployeeHourlyToModel();

	void getObjects();

}
