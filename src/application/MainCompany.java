package application;

import application.controller.CompanySimulationController;

import application.model.Company;
import application.model.CompanyInterface;

import application.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainCompany extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		CompanyInterface Model = new Company();
		View View = new View(primaryStage);
		CompanySimulationController controller = new CompanySimulationController(Model, View);
	}

	public static void main(String[] args) {
		launch(args);

	}
}
