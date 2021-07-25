package application.view;

import application.controller.CompanySimulationController;
import application.model.Department;
import application.model.Role;
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
	private ComboBox<String> employeeList = new ComboBox<String>();
	private ComboBox<Role> roleList = new ComboBox<Role>();
	private ComboBox<Department> departmentList = new ComboBox<Department>();
	private Button casualButton = new Button();
	private BorderPane mainPane;
	private BorderPane changePane;
	private TextField[] tf = { new TextField(), new TextField(), new TextField() };
	private RadioButton[] rd = { new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton() };
	private RadioButton[] rdForSalary = { new RadioButton("Hourly"), new RadioButton("Global"),
			new RadioButton("Global+Bonus") };
	private RadioButton[] rdForWorkPreference = { new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton() };
	private Button[] bt = { new Button("Add Department"), new Button("Add Role"), new Button("Add Employee"),
			new Button("Show Objects Screen"), new Button("Change Prefrence"), new Button("Show Simulations Results"),
			new Button("Save"), new Button("Exit") };
	private VBox vBoxForButtons;
	private ComboBox<Integer> c1 = new ComboBox<Integer>();
	private Image logo = new Image(getClass().getResourceAsStream("logo.png"));

	public View(Stage theStage) {
		theStage.setTitle("company simulator Systems");
		theStage.getIcons().add(logo);
		setChangePane();
		addEmployee();
		setvBox();
		setMainPane();
		Scene s = new Scene(this.mainPane, 660, 600);
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

	public VBox enterToProgramm() {
		Font font1 = new Font("SansSerif", 20);
		Font font2 = new Font("ROMAN_BASELINE", 30);
		VBox start = new VBox();
		Label l1 = new Label("Welcome to CompaNyer");
		l1.setFont(font2);
		Text t = new Text(
				"this program calculates\nthe efficiency of the workers \nbased on there work hours and prefernce");
		t.setFont(font1);
		start.setAlignment(Pos.CENTER);
		ImageView welcome = new ImageView();
		welcome.setImage(this.logo);
		welcome.setX(1);
		welcome.setY(1);
		welcome.setFitWidth(200);
		welcome.setPreserveRatio(true);
		start.getChildren().addAll(l1, t, welcome);

		return start;

	}

	public void setChangePane() {
		BorderPane changePane = new BorderPane();
		changePane.setPadding(new Insets(20));
		this.changePane = changePane;
	}

	// Department interface
	public void addDepartment() {

		VBox sp = new VBox();
		VBox enterName = new VBox();
		// name text box
		Label l1 = new Label("Enter Name: ");
		getTf()[0].setText("Enter Name Of the department");
		getTf()[0].setMaxSize(330, 100);
		enterName.getChildren().addAll(l1, getTf()[0]);
		enterName.setPadding(new Insets(15));
		enterName.setSpacing(20);
		sp.getChildren().add(enterName);
		// choose if it can have a preference
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
		// choose if the department is syncable
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
		sp.getChildren().addAll(workPreference(), OKBorderPane());
		changePane.setLeft(sp);
	}

	public void addRole() {
		VBox sp = new VBox();
		VBox enterName = new VBox();
		Label l1 = new Label("Enter Name: ");
		getTf()[0].setText("Enter Job Description");
		getTf()[0].setMaxSize(330, 100);
		enterName.getChildren().addAll(l1, getTf()[0]);
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
		HBox addDepartment = new HBox();
		Label l6 = new Label("Choose department: ");
		VBox profit = new VBox();
		Label l7 = new Label("Enter Profit: ");
		getTf()[1].setText("What is the profit for hour for this role");
		getTf()[1].setMaxSize(330, 100);
		profit.getChildren().addAll(l7, getTf()[1]);
		profit.setAlignment(Pos.CENTER_LEFT);
		profit.setSpacing(10);
		addDepartment.getChildren().addAll(l6, departmentList);
		addDepartment.setSpacing(10);
		addDepartment.setPadding(new Insets(15));
		sp.getChildren().addAll(profit, addDepartment, workPreference(), OKBorderPane());
		sp.setSpacing(10);
		sp.setAlignment(Pos.CENTER_LEFT);
		changePane.setLeft(sp);

	}

	public void addEmployee() {
		VBox sp = new VBox();
		VBox enterName = new VBox();
		Label l1 = new Label("Enter Name: ");
		getTf()[0].setText("Enter employee name");
		getTf()[0].setMaxSize(330, 100);
		getTf()[1].setText("Add year of birth");
		getTf()[1].setMaxSize(330, 100);
		Label l2 = new Label("Enter birth year: ");
		enterName.getChildren().addAll(l1, getTf()[0], l2, getTf()[1]);
		enterName.setSpacing(20);
		sp.getChildren().addAll(enterName, workPreference());
		HBox addRole = new HBox();
		Label l6 = new Label("Choose Role: ");
		addRole.getChildren().addAll(l6, roleList);
		addRole.setSpacing(10);
		addRole.setPadding(new Insets(15));
		sp.getChildren().add(addRole);
		HBox choosePreference = new HBox();
		choosePreference.setPadding(new Insets(10));
		Label l3 = new Label("Add if the Employee can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[0].setSelected(true);
		getRd()[1].setText("No");
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		choosePreference.getChildren().addAll(l3, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		sp.getChildren().add(choosePreference);
		HBox chooseSalary = new HBox();
		Label l4 = new Label("Choose salary");
		ToggleGroup tg2 = new ToggleGroup();
		chooseSalary.getChildren().add(l4);
		for (RadioButton element : rdForSalary) {
			chooseSalary.getChildren().add(element);
			tg2.getToggles().add(element);
		}
		rdForSalary[0].setSelected(true);
		chooseSalary.setAlignment(Pos.CENTER);
		chooseSalary.setSpacing(8);
		BorderPane bp = new BorderPane();
		HBox enterSalary = new HBox();
		enterSalary.setSpacing(10);
		enterSalary.setAlignment(Pos.CENTER);
		Label enterSalaryText = new Label();
		sp.getChildren().add(chooseSalary);
		enterSalaryText.setText("Add your salary for hour: ");
		getTf()[2].setText("Add your salary for hour");
		enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
		bp.setCenter(enterSalary);

		rdForSalary[0].setOnAction(e -> {
			if (rdForSalary[0].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your salary for hour: ");
				getTf()[2].setText("Add your salary for hour");
				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);
			}
		});
		rdForSalary[1].setOnAction(e -> {
			if (rdForSalary[1].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your global salary ");
				getTf()[2].setText("Add your global salary ");
				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);

			}
		});
		rdForSalary[2].setOnAction(e -> {
			if (rdForSalary[2].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your global salary ");
				getTf()[2].setText("Add your global salary ");
				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);
			}
		});
		bp.setPadding(new Insets(15));
		sp.getChildren().addAll(bp, OKBorderPane());
		sp.setSpacing(10);
		changePane.setLeft(sp);
	}

	public void changePrefrence() {
		VBox sp = new VBox();
		Label l1 = new Label("Choose between the options: ");
		BorderPane bp = new BorderPane();
		VBox vb1 = new VBox();
		VBox vb2 = new VBox();
		vb1.getChildren().add(l1);
		Button[] btForChange = { new Button("Employees"), new Button("Roles"), new Button("Department") };
		for (Button element : btForChange) {
			element.setMaxWidth(100);
			vb1.getChildren().add(element);
		}
		vb1.setSpacing(10);
		bp.setLeft(vb1);
		getEmployeeList().setDisable(true);
		getRoleList().setDisable(true);
		getEmployeeList().setDisable(true);
		vb2.getChildren().addAll(employeeList, roleList, departmentList);
		vb2.setSpacing(10);
		vb2.setAlignment(Pos.CENTER);
		vb2.setPadding(new Insets(30, 100, 60, 140));
		bp.setRight(vb2);
		btForChange[0].setOnAction(e -> {
			employeeList.setDisable(false);
			roleList.setDisable(true);
			departmentList.setDisable(true);

		});
		btForChange[1].setOnAction(e -> {
			getRoleList().setDisable(false);
			employeeList.setDisable(true);
			departmentList.setDisable(true);
		});
		btForChange[2].setOnAction(e -> {
			departmentList.setDisable(false);
			employeeList.setDisable(true);
			roleList.setDisable(true);

		});
		sp.getChildren().addAll(bp, workPreference(), OKBorderPane());
		sp.setSpacing(20);
		changePane.setLeft(sp);
	}

//work preference change used in all of the screens above
	public BorderPane workPreference() {
		BorderPane bp = new BorderPane();
		VBox choosWorkMethod = new VBox();
		ToggleGroup tg = new ToggleGroup();
		getRdForWorkPreference()[0].setText("Early");
		getRdForWorkPreference()[1].setText("Late");
		getRdForWorkPreference()[2].setText("Home");
		getRdForWorkPreference()[3].setText("Regular");
		getRdForWorkPreference()[3].setSelected(true);
		for (int i = 0; i < 4; i++) {
			rdForWorkPreference[i].setPadding(new Insets(4));
			choosWorkMethod.getChildren().add(rdForWorkPreference[i]);
			tg.getToggles().add(rdForWorkPreference[i]);

		}
		Label l3 = new Label("Choose work method: ");
		l3.setPadding(new Insets(15));
		bp.setLeft(l3);
		bp.setCenter(choosWorkMethod);
		HBox hb = new HBox();
		getRdForWorkPreference()[0].setOnAction(e -> {
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

		getRdForWorkPreference()[1].setOnAction(e -> {
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
		getRdForWorkPreference()[2].setOnAction(e -> {
			hb.getChildren().clear();
			bp.setRight(hb);

		});

		getRdForWorkPreference()[3].setOnAction(e -> {
			hb.getChildren().clear();
			bp.setRight(hb);
		});
		return bp;
	}

//OK button to save and exit back to main window
	public BorderPane OKBorderPane() {
		BorderPane OKButton = new BorderPane();
		getCasualButton().setText("Submit");
		OKButton.setRight(casualButton);
		OKButton.setPadding(new Insets(50, 0, 0, 0));
		return OKButton;
	}

	public TextField[] getTf() {
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

	public RadioButton[] getRdForWorkPreference() {
		return rdForWorkPreference;
	}

	public ComboBox<Role> getRoleList() {
		return roleList;
	}

	public RadioButton[] getRdForSalary() {
		return rdForSalary;
	}

	public ComboBox<String> getEmployeeList() {
		return employeeList;
	}

	@Override
	public void addEmployeeEvent(String name) {
		this.employeeList.getItems().add(name);
	}

	@Override
	public void addRoleEvent(String jobTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDepartmentEvent(String name) {
		// TODO Auto-generated method stub

	}

}