package application.view;

import java.util.Vector;

import application.controller.CompanySimulationController;

public interface AbstractView {

	void registerListener(CompanySimulationController companySimulationController);

	void addEmployeeEvent(String name);// add to employees combobox

	void addRoleEvent(String jobTitle);// add to roles combobox

	void addDepartmentEvent(String name);// add to departments combobox

}
