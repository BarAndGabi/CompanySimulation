package application.view;

import application.controller.CompanySimulationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View implements AbstractView {

	private BorderPane mainPane;
	private BorderPane changePane;
	private TextField tf = new TextField();
	private RadioButton[] rd = { new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton(), new RadioButton() };
	private Button[] bt = { new Button("Add Department"), new Button("Add Role"), new Button("Add Employee"),
			new Button("Show Screen"), new Button("Change Prefrence"), new Button("Show Simulations Results"),
			new Button("Save"), new Button("Exit") };
	private VBox vBoxForButtons;
	private Image logo = new Image(getClass().getResourceAsStream("logo.png"));

	public View(Stage theStage) {

		theStage.setTitle("company simulator Systems");

		theStage.getIcons().add(logo);
		setChangePane();
		setvBox();
		setMainPane();
		Scene s = new Scene(this.mainPane, 500, 400);
		s.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
		theStage.setScene(s);
		theStage.show();
		theStage.setResizable(false);
	}

	public BorderPane getMainPane() {
		return mainPane;
	}

	public void setMainPane() {
		BorderPane mainPane = new BorderPane();
		mainPane.setLeft(vBoxForButtons);
		mainPane.setCenter(changePane);
		this.mainPane = mainPane;
	}

	public Button[] getBt() {
		return bt;
	}

	public VBox getvBox() {
		return vBoxForButtons;
	}

	public void setvBox() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(10));
		for (Button element : bt) {
			element.setMaxWidth(200);
			vBox.getChildren().add(element);
		}
		vBox.setStyle("-fx-background-color: blue");
		vBox.setSpacing(20);
		this.vBoxForButtons = vBox;
	}

	public BorderPane getChangePane() {
		return changePane;
	}

	public void setChangePane() {
		BorderPane changePane = new BorderPane();
		changePane.setPadding(new Insets(20));
		this.changePane = changePane;
	}

	public void addDepartment() {
		VBox sp = new VBox();
		HBox enterName = new HBox();
		enterName.setMaxSize(300, 400);
		enterName.setAlignment(Pos.CENTER);
		Label l1 = new Label("Enter Name: ");
		getTf().setText("Enter Name Of the department");
		enterName.getChildren().addAll(l1, getTf());
		sp.getChildren().add(enterName);
		HBox choosePreference = new HBox();
		Label l2 = new Label("Add if the Department can change it's preference");
		getRd()[0].setText("Yes");
		getRd()[1].setText("No");
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		sp.getChildren().add(choosePreference);
		sp.setAlignment(Pos.CENTER);
		sp.setSpacing(20);
		changePane.setLeft(sp);
	}



	public TextField getTf() {
		return tf;
	}

	public RadioButton[] getRd() {
		return rd;
	}

	@Override
	public void registerListener(CompanySimulationController companySimulationController) {

	}

}