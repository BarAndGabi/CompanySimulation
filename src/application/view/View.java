package application.view;

import application.controller.CompanySimulationController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View implements AbstractView {
	public View(Stage theStage) {
		Scene s = new Scene(null);
		theStage.setScene(s);
		theStage.show();
	}
	@Override
	public void registerListener(CompanySimulationController companySimulationController) {
		// TODO Auto-generated method stub
		
	}

}
