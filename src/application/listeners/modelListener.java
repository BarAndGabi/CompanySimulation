package application.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.model.Department;
import application.model.Employee;
import application.model.Role;

public interface modelListener {

	void createAddDepartmentEvent(Department d);

	void createAddRoleEvent(Role r);

	void createAddEmployeeEvent(Employee e);

	void save() throws FileNotFoundException, IOException;

}
