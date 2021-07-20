package application.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View implements AbstractView {
	public View(Stage theStage) {
		Scene s = new Scene(null);
		theStage.setScene(s);
		theStage.show();
	}

}
