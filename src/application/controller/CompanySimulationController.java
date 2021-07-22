package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

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

	public CompanySimulationController(CompanyInterface model, View view) throws Exception {
		try {
			this.Model = this.loadFileEvent();
		} catch (FileNotFoundException e) {
			this.Model = model;
		}
		this.View = view;
		Model.registerListener(this);
		View.registerListener(this);
	}

	public CompanyInterface loadFileEvent() throws Exception {
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Elections.dat"));
			CompanyInterface fileArchive = ((CompanyInterface) inFile.readObject());
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

	@Override
	public void save() throws FileNotFoundException, IOException {
		this.Model.saveFile();
	}
}
