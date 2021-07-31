package Gabriel_Lempert;

import Gabriel_Lempert.controller.CompanySimulationController;
import Gabriel_Lempert.model.Company;
import Gabriel_Lempert.model.CompanyInterface;
import Gabriel_Lempert.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainCompany extends Application {
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
