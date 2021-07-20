package application.listeners;

import application.model.Department;
import application.model.Employee;
import application.model.Role;

public interface modelListener {

	void createAddDepartmentEvent(Department d);

	void createAddRoleEvent(Role r);

	void createAddEmployeeEvent(Employee e);

}
