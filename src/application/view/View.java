package application.view;

import application.controller.CompanySimulationController;
import application.model.Department;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View implements AbstractView {
	private ComboBox<Department> departmentList = new ComboBox<Department>();
	private Button casualButton = new Button();
	private BorderPane mainPane;
	private BorderPane changePane;
	private TextField tf = new TextField();
	private RadioButton[] rd = { new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton() };
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
		// addRole();
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
		Font font1 = new Font("SansSerif", 20);
		Font font2 = new Font("ROMAN_BASELINE", 30);
		BorderPane changePane = new BorderPane();
		VBox start = new VBox();
		changePane.setPadding(new Insets(20));
		Label l1 = new Label("Welcome to CompaNyer");
		l1.setFont(font2);
		Text t = new Text(
				"this program calculates\nthe efficiency of the workers \nbased on there work hours and prefernce");
		t.setFont(font1);
		start.setAlignment(Pos.CENTER);
		ImageView welcome = new ImageView("file:///C:/Users/lempe/git/CompanySimulation/src/application/view/logo.png");
		start.getChildren().addAll(l1, t);
		changePane.setLeft(start);
		this.changePane = changePane;
	}

	// Department interface
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
		Label l2 = new Label("Add if the Department can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[1].setText("No");
		getRd()[0].setSelected(true);
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		sp.getChildren().add(choosePreference);
		HBox syncAble = new HBox();
		syncAble.setPadding(new Insets(10));
		Label l4 = new Label("Add if the Department is SYNCED");
		l4.setPadding(new Insets(0, 83, 40, 0));
		ToggleGroup tg2 = new ToggleGroup();
		getRd()[2].setText("Yes");
		getRd()[2].setSelected(true);
		getRd()[3].setText("No");
		tg2.getToggles().addAll(getRd()[2], getRd()[3]);
		syncAble.getChildren().addAll(l4, getRd()[2], getRd()[3]);
		syncAble.setSpacing(20);
		sp.getChildren().add(syncAble);
		BorderPane bp = new BorderPane();
		VBox choosWorkMethod = new VBox();
		ToggleGroup tg = new ToggleGroup();
		getRd()[4].setText("Early");
		getRd()[4].setSelected(true);
		getRd()[5].setText("Late");
		getRd()[6].setText("Home");
		getRd()[7].setText("Regular");
		for (int i = 4; i < rd.length; i++) {
			rd[i].setPadding(new Insets(4));
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
			Label l5 = new Label("how early would you like to be");
			getC1().getItems().clear();
			for (int i = 1; i < 9; i++) {
				getC1().getItems().add(i);
			}
			getC1().setValue(1);
			hb.getChildren().addAll(l5, getC1());
			hb.setSpacing(15);
			bp.setRight(hb);

		});

		getRd()[5].setOnAction(e -> {
			hb.getChildren().clear();
			Label l5 = new Label("how late would you like to be");
			getC1().getItems().clear();
			for (int i = 1; i < 8; i++) {
				getC1().getItems().add(i);
			}
			getC1().setValue(1);
			hb.getChildren().addAll(l5, getC1());
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
		BorderPane OKButton = new BorderPane();
		getCasualButton().setText("OK");
		OKButton.setRight(casualButton);
		sp.getChildren().add(OKButton);
		changePane.setLeft(sp);
	}

	public void addRole() {
		VBox sp = new VBox();
		VBox enterName = new VBox();
		Label l1 = new Label("Enter Name: ");
		getTf().setText("Enter Job Description");
		enterName.getChildren().addAll(l1, getTf());
		enterName.setPadding(new Insets(15));
		enterName.setSpacing(20);
		sp.getChildren().add(enterName);
		HBox choosePreference = new HBox();
		choosePreference.setPadding(new Insets(10));
		Label l2 = new Label("Add if the Role can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[0].setSelected(true);
		getRd()[1].setText("No");
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		sp.getChildren().add(choosePreference);
		HBox syncAble = new HBox();
		syncAble.setPadding(new Insets(10));
		Label l4 = new Label("Add if the Role is SYNCED");
		l4.setPadding(new Insets(0, 83, 0, 0));
		ToggleGroup tg2 = new ToggleGroup();
		getRd()[2].setText("Yes");
		getRd()[2].setSelected(true);
		getRd()[3].setText("No");
		tg2.getToggles().addAll(getRd()[2], getRd()[3]);
		syncAble.getChildren().addAll(l4, getRd()[2], getRd()[3]);
		syncAble.setSpacing(20);
		sp.getChildren().add(syncAble);
		HBox homeWorking = new HBox();
		Label l5 = new Label("Add if the Role \ngives an option to work from home");
		ToggleGroup tg3 = new ToggleGroup();
		getRd()[4].setText("Yes");
		getRd()[4].setSelected(true);
		getRd()[5].setText("No");
		tg3.getToggles().addAll(getRd()[4], getRd()[5]);
		homeWorking.getChildren().addAll(l5, getRd()[4], getRd()[5]);
		homeWorking.setSpacing(20);
		l5.setPadding(new Insets(10, 29, 0, 10));
		homeWorking.setPadding(new Insets(5));
		sp.getChildren().add(homeWorking);
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

	public Button getCasualButton() {
		return casualButton;
	}

	public ComboBox<Department> getDepartmentList() {
		return departmentList;
	}
}