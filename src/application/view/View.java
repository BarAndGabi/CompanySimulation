package application.view;

import application.controller.CompanySimulationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
			new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton() };
	private Button[] bt = { new Button("Add Department"), new Button("Add Role"), new Button("Add Employee"),
			new Button("Show Screen"), new Button("Change Prefrence"), new Button("Show Simulations Results"),
			new Button("Save"), new Button("Exit") };
	private VBox vBoxForButtons;
	private ComboBox<Integer> c1 = new ComboBox<Integer>();
	private Image logo = new Image(getClass().getResourceAsStream("logo.png"));

	public View(Stage theStage) {

		theStage.setTitle("company simulator Systems");
		theStage.getIcons().add(logo);
		setChangePane();
		addDepartment();
		setvBox();
		setMainPane();
		Scene s = new Scene(this.mainPane, 660, 400);
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
		vBox.setStyle("-fx-background-color: #183492");
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

	/**
	 *
	 */
	public void addDepartment() {
		VBox sp = new VBox();
		VBox enterName = new VBox();

		Label l1 = new Label("Enter Name: ");
		getTf().setText("Enter Name Of the department");
		enterName.getChildren().addAll(l1, getTf());
		enterName.setPadding(new Insets(15));
		enterName.setSpacing(20);
		sp.getChildren().add(enterName);
		HBox choosePreference = new HBox();
		choosePreference.setPadding(new Insets(10));
		choosePreference.setSpacing(15);
		Label l2 = new Label("Add if the Department can change it's preference");
		getRd()[0].setText("Yes");
		getRd()[1].setText("No");
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		sp.getChildren().add(choosePreference);
		BorderPane bp = new BorderPane();
		VBox choosWorkMethod = new VBox();
		ToggleGroup tg = new ToggleGroup();
		getRd()[4].setText("Early");
		getRd()[5].setText("Late");
		getRd()[6].setText("Home");
		getRd()[7].setText("Regular");
		for (int i = 4; i < rd.length; i++) {
			choosWorkMethod.getChildren().add(rd[i]);
			tg.getToggles().add(rd[i]);

		}
		Label l3 = new Label("Choose work method: ");
		l3.setPadding(new Insets(15));
		bp.setLeft(l3);
		bp.setCenter(choosWorkMethod);
		HBox hb = new HBox();
		getRd()[4].setOnAction(e -> {
			hb.getChildren().clear();
			Label l4 = new Label("how early would you like to be");
			getC1().getItems().clear();
			for (int i = 1; i < 9; i++) {
				getC1().getItems().add(i);
			}
			getC1().setValue(1);
			hb.getChildren().addAll(l4, getC1());
			hb.setSpacing(15);
			bp.setRight(hb);

		});

		getRd()[5].setOnAction(e -> {
			hb.getChildren().clear();
			Label l4 = new Label("how late would you like to be");
			getC1().getItems().clear();
			for (int i = 1; i < 8; i++) {
				getC1().getItems().add(i);
			}
			getC1().setValue(1);
			hb.getChildren().addAll(l4, getC1());
			hb.setSpacing(15);
			bp.setRight(hb);
		});
		getRd()[6].setOnAction(e -> {
			hb.getChildren().clear();
			bp.setRight(hb);

		});
		getRd()[7].setOnAction(e -> {
			hb.getChildren().clear();
			bp.setRight(hb);
		});
		sp.getChildren().add(bp);
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

	public ComboBox<Integer> getC1() {
		return c1;
	}

	public void setC1() {
		ComboBox<Integer> c1 = new ComboBox<Integer>();
		this.c1 = c1;
	}
}