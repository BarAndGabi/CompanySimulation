package application;

import application.controller.CompanySimulationController;

import application.model.Company;
import application.model.CompanyInterface;

import application.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class CompanySimulationsMAIN extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		View View = new View(primaryStage);
		CompanyInterface Model = new Company();
		CompanySimulationController controller = new CompanySimulationController(Model, View);
	}

	public static void main(String[] args) {
		launch(args);

	}
}