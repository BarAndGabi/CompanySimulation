package Gabriel_Lempert.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import Gabriel_Lempert.model.Department;
import Gabriel_Lempert.model.Employee;
import Gabriel_Lempert.model.Role;

public interface modelListener {

	void createAddDepartmentEvent(Department d);

	void createAddRoleEvent(Role r);

	void createAddEmployeeEvent(Employee e);

	void save() throws FileNotFoundException, IOException;

}
