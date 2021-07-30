package application.view;

import java.io.Serializable;
import java.util.ArrayList;

import application.listeners.UIEventListener;
import application.model.Preference;
import application.model.PreferenceType;
import application.model.objectType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View implements AbstractView, Serializable {
	private String name = new String();
	private int choise = 0;
	private ScrollPane toStringForEach = new ScrollPane();
	private objectType objectType;
	private boolean syncd = false;
	private boolean chooseP = false;
	private boolean workHome = false;
	private ComboBox<String> employeeList = new ComboBox<String>();
	private ComboBox<String> roleList = new ComboBox<String>();
	private ComboBox<String> departmentList = new ComboBox<String>();
	private ArrayList<UIEventListener> listeners;
	private VBox sp = new VBox();
	private Button companyButton = new Button("Company");
	private Button casualButton = new Button();
	private BorderPane mainPane;
	private BorderPane changePane;
	private TextField[] tf = { new TextField(), new TextField(), new TextField() };
	private RadioButton[] rd = { new RadioButton(), new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton(), new RadioButton() };
	private RadioButton[] rdForSalary = { new RadioButton("Hourly"), new RadioButton("Global"),
			new RadioButton("Global+Bonus") };
	private RadioButton[] rdForWorkPreference = { new RadioButton(), new RadioButton(), new RadioButton(),
			new RadioButton() };
	private Button[] bt = { new Button("Add Department"), new Button("Add Role"), new Button("Add Employee"),
			new Button("Change Prefrence"), new Button("Show Objects"), new Button("Show Results"), new Button("Save"),
			new Button("Exit") };
	private VBox vBoxForButtons;
	private VBox vBoxForButtonsDown;

	private ComboBox<Integer> c1 = new ComboBox<Integer>();
	private Image logo = new Image(getClass().getResourceAsStream("logo.png"));
	private Stage s;
	private int hourChange;

	public View(Stage theStage) {
		sp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		s = theStage;
		this.listeners = new ArrayList<UIEventListener>();
		this.roleList.setMaxWidth(Double.MAX_VALUE);
		this.employeeList.setMaxWidth(Double.MAX_VALUE);
		this.departmentList.setMaxWidth(Double.MAX_VALUE);
		rd[0].setSelected(true);
		rd[2].setSelected(true);
		rd[4].setSelected(true);
		rdForWorkPreference[2].setSelected(true);
		theStage.setTitle("company simulator Systems");
		theStage.getIcons().add(logo);
		setChangePane();
		changePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		changePanels();
		setvBox();
		setvBoxDown();
		setMainPane();
		Scene s = new Scene(this.mainPane, 630, 700);
		s.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
		theStage.setScene(s);
		theStage.show();
		theStage.setResizable(false);
		this.casualButton.setStyle(" -fx-background-color: #2E4980;\n" + "    -fx-background-radius: 30;\n"
				+ "    -fx-background-insets: 0;\n" + "    -fx-text-fill: white;\n");
		this.casualButton.setCursor(Cursor.HAND);
		this.sp.setAlignment(Pos.CENTER);

	}

	public BorderPane getMainPane() {
		return mainPane;
	}

	public void changePanels() {
		getBt()[0].setOnAction(e -> {
			getSp().getChildren().clear();
			addDepartment();
			setRdButtonSelected();
		});
		getBt()[1].setOnAction(e -> {
			this.sp.getChildren().clear();
			addRole();
			setRdButtonSelected();
			this.departmentList.setDisable(false);
			this.departmentList.setValue("");
			this.departmentList.setMaxWidth(100);
		});
		getBt()[2].setOnAction(e -> {
			this.sp.getChildren().clear();
			addEmployee();
			this.roleList.setDisable(false);
			this.roleList.setValue("");
			this.roleList.setMaxWidth(100);
			setRdButtonSelected();

		});
		getBt()[4].setOnAction(e -> {
			getSp().getChildren().clear();
			this.toStringForEach.setVisible(false);
			showObjects();
			this.employeeList.setMaxWidth(Double.MAX_VALUE);
			this.roleList.setMaxWidth(Double.MAX_VALUE);
			this.departmentList.setMaxWidth(Double.MAX_VALUE);
			this.employeeList.setValue("");
			this.roleList.setValue("");
			this.departmentList.setValue("");
		});

		getBt()[3].setOnAction(e -> {
			this.sp.getChildren().clear();
			this.employeeList.setOnAction(e1 -> {

			});
			this.roleList.setOnAction(e1 -> {

			});
			this.departmentList.setOnAction(e1 -> {

			});
			changePrefrence();
			this.employeeList.setMaxWidth(100);
			this.roleList.setMaxWidth(100);
			this.departmentList.setMaxWidth(100);
			this.employeeList.setValue("");
			this.roleList.setValue("");
			this.departmentList.setValue("");
			setRdButtonSelected();
		});
		getBt()[5].setOnAction(e -> {
			this.sp.getChildren().clear();
			this.toStringForEach.setVisible(false);
			this.employeeList.setOnAction(e1 -> {

			});
			this.roleList.setOnAction(e1 -> {

			});
			this.departmentList.setOnAction(e1 -> {

			});
			showResutls();
			this.employeeList.setMaxWidth(Double.MAX_VALUE);
			this.roleList.setMaxWidth(Double.MAX_VALUE);
			this.departmentList.setMaxWidth(Double.MAX_VALUE);
			this.employeeList.setValue("");
			this.roleList.setValue("");
			this.departmentList.setValue("");

		});
		getBt()[6].setOnAction(e -> {
			this.sp.getChildren().clear();
			this.save();
		});
		getBt()[7].setOnAction(e -> {
			this.quitApp(this.s);

		});
	}

	private void save() {
		for (int i = 0; i < listeners.size(); i++) {
			try {
				this.listeners.get(i).save();
				this.loadSucssesAlert();
				this.changePane.setCenter(enterToProgramm());
			} catch (Exception e) {
				this.exceptionAlert(e);
			}
		}
	}

	public void setMainPane() {
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(vBoxForButtons);
		mainPane.setCenter(changePane);
		mainPane.setBottom(this.vBoxForButtonsDown);

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
		ToolBar tb1 = new ToolBar();
		tb1.setMaxWidth(Double.MAX_VALUE);

		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(10));
		for (int i = 0; i < 6; i++) {
			this.bt[i].setMinWidth(Double.MIN_VALUE);
			tb1.getItems().add(this.bt[i]);
		}
		vBox.getChildren().add(tb1);
		vBox.setStyle("-fx-background-color: #087DD4");
		vBox.setSpacing(1);
		this.vBoxForButtons = vBox;
	}

	public void setvBoxDown() {
		VBox vBox = new VBox();
		ToolBar tb2 = new ToolBar();
		tb2.setMaxWidth(100);
		vBox.setAlignment(Pos.CENTER_RIGHT);
		vBox.setPadding(new Insets(10));

		for (int i = 6; i < this.bt.length; i++) {
			this.bt[i].setMinWidth(Double.MIN_VALUE);
			tb2.getItems().add(this.bt[i]);
		}
		vBox.getChildren().add(tb2);

		vBox.setStyle("-fx-background-color: #087DD4");
		vBox.setSpacing(1);
		this.vBoxForButtonsDown = vBox;
	}

	public BorderPane getChangePane() {
		return changePane;
	}

	public void showObjects() {
		HBox hb = new HBox();
		BorderPane bp = new BorderPane();
		Label l1 = new Label("Choose which object you want to show");
		HBox hb1 = new HBox(l1);
		this.sp.getChildren().clear();
		hb1.setAlignment(Pos.CENTER);
		Button[] btForShow = { new Button("Employee"), new Button("Roles"), new Button("Department"), new Button() };
		btForShow[3] = this.companyButton;
		for (Button element : btForShow) {
			element.setMaxWidth(Double.MAX_VALUE);
			hb.getChildren().add(element);
		}
		hb.setSpacing(20);
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(20, 30, 40, 60));
		sp.getChildren().addAll(hb1, hb, bp);
		btForShow[0].setOnAction(e -> {
			this.employeeList.setDisable(false);
			bp.setCenter(this.employeeList);
		});
		btForShow[1].setOnAction(e -> {
			this.roleList.setDisable(false);
			bp.setCenter(this.roleList);
		});
		btForShow[2].setOnAction(e -> {
			this.departmentList.setDisable(false);
			bp.setCenter(this.departmentList);
		});
		this.sp.setAlignment(Pos.TOP_CENTER);
		Text text = new Text();
		toStringForEach.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		toStringForEach.setPadding(new Insets(20, 30, 40, 50));
		this.sp.getChildren().add(toStringForEach);
		int size = sp.getChildren().size();
		this.sp.getChildren().get(size - 1).setVisible(false);
		this.sp.setSpacing(10);
		this.changePane.setCenter(sp);
		this.departmentList.setOnAction(e -> {
			try {
				sp.getChildren().get(size - 1).setVisible(true);
				for (UIEventListener listener : listeners) {
					String st = listener.getObjectToString(objectType.DEPARTMENT, departmentList.getValue());
					text.setText(st);
					toStringForEach.setContent(text);
				}
			} catch (Exception e1) {
				this.exceptionAlert(e1);
			}
		});

		this.roleList.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				this.sp.getChildren().get(size - 1).setVisible(true);
				try {
					String st = listener.getObjectToString(objectType.ROLE, roleList.getValue());
					text.setText(st);
					this.toStringForEach.setContent(text);
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}

		});
		this.employeeList.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				this.sp.getChildren().get(size - 1).setVisible(true);
				try {
					String st = listener.getObjectToString(objectType.EMPLOYEE, this.employeeList.getValue());
					text.setText(st);
					toStringForEach.setContent(text);
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}
		});
		btForShow[3].setOnAction(e -> {
			this.sp.getChildren().get(size - 1).setVisible(true);
			for (UIEventListener listener : listeners) {
				String st = listener.getCompanyToString();
				text.setText(st);
				toStringForEach.setContent(text);
			}
		});

	}

	public VBox enterToProgramm() {
		Font font1 = new Font("SansSerif", 17.5);
		Font font2 = new Font("ROMAN_BASELINE", 25);
		VBox start = new VBox();
		Label l1 = new Label("Welcome to company simulation program");
		l1.setFont(font2);
		Text t = new Text(
				"\nthis program calculates the efficiency of the workers, \nbased on their work hours and prefernce.\n");
		t.setFont(font1);
		ImageView welcome = new ImageView();
		welcome.setImage(this.logo);
		welcome.setX(1);
		welcome.setY(1);
		welcome.setFitWidth(275);
		welcome.setPreserveRatio(true);
		start.getChildren().addAll(l1, t);
		start.getChildren().add(welcome);
		start.setAlignment(Pos.CENTER);

		return start;

	}

	public void setChangePane() {
		BorderPane changePane = new BorderPane();
		changePane.setPadding(new Insets(20));
		changePane.setCenter(enterToProgramm());
		this.changePane = changePane;
	}

	// Department interface
	public void addDepartment() {
		VBox enterName = new VBox();
		// name text box
		this.sp.getChildren().clear();
		this.sp.setAlignment(Pos.TOP_CENTER);
		getTf()[0].setText("Enter Name Of the department");
		getTf()[0].setMaxSize(330, 100);
		getTf()[0].setOnMouseClicked(e -> {
			getTf()[0].clear();
		});
		enterName.setAlignment(Pos.BASELINE_CENTER);
		enterName.getChildren().addAll(getTf()[0]);
		enterName.setPadding(new Insets(15));
		enterName.setSpacing(20);
		this.sp.getChildren().add(enterName);
		// choose if it can have a preference
		HBox choosePreference = new HBox();
		choosePreference.setAlignment(Pos.CENTER);
		choosePreference.setPadding(new Insets(10));
		Label l2 = new Label("Add if the Department can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[1].setText("No");
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		this.sp.getChildren().add(choosePreference);
		// choose if the department is syncable
		HBox syncAble = new HBox();
		syncAble.setAlignment(Pos.BASELINE_CENTER);
		syncAble.setPadding(new Insets(10));
		Label l4 = new Label("Add if the Department is SYNCED");
		l4.setPadding(new Insets(0, 83, 40, 0));
		ToggleGroup tg2 = new ToggleGroup();
		getRd()[2].setText("Yes");
		getRd()[3].setText("No");
		tg2.getToggles().addAll(getRd()[2], getRd()[3]);
		syncAble.getChildren().addAll(l4, getRd()[2], getRd()[3]);
		syncAble.setSpacing(20);
		this.sp.getChildren().add(syncAble);
		this.sp.getChildren().addAll(workPreference(), OKBorderPane());
		this.sp.setSpacing(15);
		this.changePane.setCenter(sp);
		this.casualButton.setOnAction(e -> {
			ChooseP();
			for (UIEventListener listener : listeners) {
				try {
					listener.addDepartmentToModel(getTf()[0].getText(), syncd, chooseP, choosePrefrenceNav(),
							hourChange);
					this.loadSucssesAlert();
					this.changePane.setCenter(enterToProgramm());
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}
		});
	}

	public void addRole() {
		VBox enterName = new VBox();
		enterName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.sp.getChildren().clear();
		getTf()[0].setText("Enter Job Description");
		getTf()[0].setMaxSize(330, 100);
		getTf()[0].setOnMouseClicked(e -> {
			getTf()[0].clear();
		});
		enterName.getChildren().addAll(getTf()[0]);
		enterName.setSpacing(20);
		this.sp.getChildren().add(enterName);
		HBox choosePreference = new HBox();
		choosePreference.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		choosePreference.setPadding(new Insets(10));
		Label l2 = new Label("Add if the Role can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[1].setText("No");
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		choosePreference.getChildren().addAll(l2, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		this.sp.getChildren().add(choosePreference);
		HBox syncAble = new HBox();
		syncAble.setPadding(new Insets(10));
		Label l4 = new Label("Add if the Role is SYNCED");
		l4.setPadding(new Insets(0, 83, 0, 0));
		ToggleGroup tg2 = new ToggleGroup();
		getRd()[2].setText("Yes");
		getRd()[3].setText("No");
		tg2.getToggles().addAll(getRd()[2], getRd()[3]);
		syncAble.getChildren().addAll(l4, getRd()[2], getRd()[3]);
		syncAble.setSpacing(20);
		this.sp.getChildren().add(syncAble);
		HBox homeWorking = new HBox();
		Label l5 = new Label("Add if the Role \ngives an option to work from home");
		ToggleGroup tg3 = new ToggleGroup();
		getRd()[4].setText("Yes");
		getRd()[5].setText("No");
		tg3.getToggles().addAll(getRd()[4], getRd()[5]);
		homeWorking.getChildren().addAll(l5, getRd()[4], getRd()[5]);
		homeWorking.setSpacing(20);
		l5.setPadding(new Insets(10, 29, 0, 10));
		homeWorking.setPadding(new Insets(5));
		this.sp.getChildren().add(homeWorking);
		HBox addDepartment = new HBox();
		Label l6 = new Label("Choose department: ");
		VBox profit = new VBox();
		getTf()[1].setText("Enter the profit for hour for this role");
		getTf()[1].setMaxSize(330, 100);
		getTf()[1].setOnMouseClicked(e -> {
			getTf()[1].clear();
		});
		profit.getChildren().add(getTf()[1]);
		profit.setAlignment(Pos.CENTER_LEFT);
		profit.setSpacing(10);
		this.departmentList.setMaxWidth(Double.MAX_VALUE);
		addDepartment.getChildren().addAll(l6, this.departmentList);
		addDepartment.setSpacing(10);
		addDepartment.setPadding(new Insets(15));
		this.sp.getChildren().addAll(profit, addDepartment, workPreference(), OKBorderPane());
		this.sp.setSpacing(10);
		this.sp.setAlignment(Pos.CENTER);
		this.changePane.setCenter(this.sp);
		this.casualButton.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				try {
					ChooseP();
					Preference p = new Preference(choosePrefrenceNav(), hourChange);
					listener.addRoleToModel(Double.valueOf(getTf()[1].getText()), getTf()[0].getText(), syncd,
							departmentList.getValue(), p, workHome, chooseP);
					this.loadSucssesAlert();
					this.changePane.setCenter(enterToProgramm());
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}
		});

	}

	public void addEmployee() {
		VBox enterName = new VBox();
		this.sp.getChildren().clear();
		this.sp.setAlignment(Pos.CENTER);

		getTf()[0].setText("Enter employee name");
		getTf()[0].setMaxSize(330, 100);
		getTf()[0].setOnMouseClicked(e -> {
			getTf()[0].clear();
		});
		getTf()[1].setText("Add year of birth");
		getTf()[1].setMaxSize(330, 100);
		getTf()[1].setOnMouseClicked(e -> {
			getTf()[1].clear();
		});

		enterName.getChildren().addAll(getTf()[0], getTf()[1]);
		enterName.setSpacing(20);
		this.sp.getChildren().addAll(enterName, workPreference());
		HBox addRole = new HBox();
		Label l6 = new Label("Choose Role: ");
		addRole.getChildren().addAll(l6, roleList);
		addRole.setSpacing(10);
		addRole.setPadding(new Insets(15));
		this.sp.getChildren().add(addRole);
		HBox choosePreference = new HBox();
		choosePreference.setPadding(new Insets(10));
		Label l3 = new Label("Add if the Employee can change it's preference");
		ToggleGroup tg1 = new ToggleGroup();
		getRd()[0].setText("Yes");// yes if the department can change preference
		getRd()[1].setText("No");
		tg1.getToggles().addAll(getRd()[0], getRd()[1]);
		this.rd[0].setSelected(true);
		choosePreference.getChildren().addAll(l3, getRd()[0], getRd()[1]);
		choosePreference.setSpacing(20);
		getSp().getChildren().add(choosePreference);
		HBox chooseSalary = new HBox();
		Label l4 = new Label("Choose salary");
		ToggleGroup tg2 = new ToggleGroup();
		chooseSalary.getChildren().add(l4);
		for (RadioButton element : rdForSalary) {
			chooseSalary.getChildren().add(element);
			tg2.getToggles().add(element);
		}
		this.rdForSalary[0].setSelected(true);
		chooseSalary.setSpacing(8);
		BorderPane bp = new BorderPane();
		HBox enterSalary = new HBox();
		enterSalary.setSpacing(10);
		Label enterSalaryText = new Label();
		this.sp.getChildren().add(chooseSalary);
		enterSalaryText.setText("Add your salary for hour: ");
		getTf()[2].setText("salary");
		getTf()[2].setOnMouseClicked(e -> {
			getTf()[2].clear();
		});

		enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
		bp.setCenter(enterSalary);

		this.rdForSalary[0].setOnAction(e -> {
			if (this.rdForSalary[0].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your salary for hour: ");
				getTf()[2].setText("salary");
				getTf()[2].setOnMouseClicked(e1 -> {
					getTf()[2].clear();
				});
				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);
			}
		});
		this.rdForSalary[1].setOnAction(e -> {
			if (this.rdForSalary[1].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your global salary ");
				getTf()[2].setText("salary");
				getTf()[2].setOnMouseClicked(e1 -> {
					getTf()[2].clear();
				});

				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);

			}
		});
		this.rdForSalary[2].setOnAction(e -> {
			if (rdForSalary[2].isSelected()) {
				enterSalary.getChildren().clear();
				enterSalaryText.setText("Add your global salary ");
				getTf()[2].setText("Add your global salary ");
				getTf()[2].setOnMouseClicked(e1 -> {
					getTf()[2].clear();
				});
				enterSalary.getChildren().addAll(enterSalaryText, getTf()[2]);
				bp.setCenter(enterSalary);
			}
		});
		bp.setPadding(new Insets(15));
		getSp().getChildren().addAll(bp, OKBorderPane());
		getSp().setSpacing(10);
		this.casualButton.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				try {
					Preference p = new Preference(choosePrefrenceNav(), hourChange);
					if (this.rdForSalary[2].isSelected()) {
						listener.addEmployeeGlobalyPlusToModel(getTf()[0].getText(),
								Integer.valueOf(getTf()[1].getText()), p, Integer.valueOf(getTf()[2].getText()),
								this.roleList.getValue(), chooseP);
					}
					if (this.rdForSalary[1].isSelected()) {
						listener.addEmployeeGlobalyToModel(getTf()[0].getText(), Integer.valueOf(getTf()[1].getText()),
								p, Integer.valueOf(getTf()[2].getText()), this.roleList.getValue(), chooseP);
					}
					if (this.rdForSalary[0].isSelected()) {
						listener.addEmployeeHourlyToModel(getTf()[0].getText(), Integer.valueOf(getTf()[1].getText()),
								p, Integer.valueOf(getTf()[2].getText()), this.roleList.getValue(), chooseP);

					}
					this.loadSucssesAlert();
					this.changePane.setCenter(enterToProgramm());
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}

			}
		});
		this.changePane.setCenter(getSp());
	}

	public void changePrefrence() {
		getSp().getChildren().clear();
		this.sp.setAlignment(Pos.TOP_CENTER);
		Label l1 = new Label("Choose the objectType to change from above \nthen choose from the list below:\n \n\n");
		BorderPane bp = new BorderPane();
		VBox vb2 = new VBox();
		ToolBar tb = new ToolBar();
		tb.setAccessibleText("Choose the objectType to change from above \nthen choose from the list below:\n \n\n");
		vb2.getChildren().add(l1);
		bp.setAlignment(tb, Pos.CENTER);
		tb.setMaxWidth(230);
		vb2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		vb2.setAlignment(Pos.CENTER);
		Button[] btForChange = { new Button("Employees"), new Button("Roles"), new Button("Department") };
		for (Button element : btForChange) {
			element.setMaxWidth(Double.MAX_VALUE);
			// vb1.getChildren().add(element);
			tb.getItems().add(element);
		}
		bp.setTop(tb);
		getEmployeeList().setDisable(true);
		getRoleList().setDisable(true);
		getDepartmentList().setDisable(true);
		vb2.setSpacing(10);
		vb2.setPadding(new Insets(1, 1, 1, 1));
		bp.setCenter(vb2);
		bp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		btForChange[0].setOnAction(e -> {
			this.objectType = objectType.EMPLOYEE;
			vb2.getChildren().clear();
			vb2.getChildren().add(l1);
			this.employeeList.setMaxWidth(Double.MAX_VALUE);
			vb2.getChildren().add(this.employeeList);
			this.employeeList.setDisable(false);
			this.roleList.setDisable(true);
			this.departmentList.setDisable(true);
			this.choise = 1;

		});
		btForChange[1].setOnAction(e -> {
			vb2.getChildren().clear();
			vb2.getChildren().add(l1);
			vb2.getChildren().add(this.roleList);
			this.roleList.setMaxWidth(Double.MAX_VALUE);
			this.objectType = objectType.ROLE;
			getRoleList().setDisable(false);
			this.employeeList.setDisable(true);
			this.departmentList.setDisable(true);
			this.choise = 2;

		});
		btForChange[2].setOnAction(e -> {
			vb2.getChildren().clear();
			vb2.getChildren().add(l1);
			vb2.getChildren().add(this.departmentList);
			this.departmentList.setMaxWidth(Double.MAX_VALUE);
			this.objectType = objectType.DEPARTMENT;
			this.departmentList.setDisable(false);
			this.employeeList.setDisable(true);
			this.roleList.setDisable(true);
			this.choise = 3;

		});

		this.sp.getChildren().addAll(bp, workPreference(), OKBorderPane());
		this.sp.setSpacing(20);
		this.changePane.setCenter(sp);
		this.casualButton.setOnAction(e -> {
			ChooseP();
			switch (choise) {
			case 1:
				this.name = employeeList.getValue();
				break;
			case 2:
				this.name = roleList.getValue();
				break;
			case 3:
				this.name = departmentList.getValue();
				break;
			}
			for (UIEventListener listener : listeners) {
				try {
					listener.choosePreference(choosePrefrenceNav(), hourChange, objectType, this.name);
					this.loadSucssesAlert();
					this.changePane.setCenter(enterToProgramm());
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}

			}
		});
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
		for (int i = 0; i < 4; i++) {
			choosWorkMethod.getChildren().add(rdForWorkPreference[i]);
			tg.getToggles().add(rdForWorkPreference[i]);

		}
		choosWorkMethod.setAlignment(Pos.CENTER_LEFT);

		Label l3 = new Label("Choose work method: ");
		l3.setPadding(new Insets(3));
		bp.setTop(l3);
		bp.setLeft(choosWorkMethod);
		choosWorkMethod.setSpacing(3);
		choosWorkMethod.setPadding(new Insets(8));
		HBox hb = new HBox();
		hb.setAlignment(Pos.BASELINE_CENTER);
		hb.setSpacing(1);
		getRdForWorkPreference()[0].setOnAction(e -> {
			hb.getChildren().clear();
			Label l5 = new Label("how early would you like to be");
			getC1().getItems().clear();
			for (int i = 1; i < 9; i++) {
				getC1().getItems().add(i);
			}
			getC1().setValue(1);
			hb.getChildren().addAll(l5, getC1());
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
		OKButton.setCenter(casualButton);
		return OKButton;
	}

	public TextField[] getTf() {
		return tf;
	}

	public RadioButton[] getRd() {
		return rd;
	}

	@Override
	public void registerListener(UIEventListener companySimulationController) {
		this.listeners.add(companySimulationController);
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

	public ComboBox<String> getDepartmentList() {
		return departmentList;
	}

	public RadioButton[] getRdForWorkPreference() {
		return rdForWorkPreference;
	}

	public ComboBox<String> getRoleList() {
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

		if (!(employeeList.getItems().isEmpty())) {
			this.employeeList.setValue(this.employeeList.getItems().get(0));
		}

	}

	@Override
	public void addRoleEvent(String jobTitle) {
		this.roleList.getItems().add(jobTitle);
		if (!(roleList.getItems().isEmpty())) {
			this.roleList.setValue(this.roleList.getItems().get(0));
		}
	}

	@Override
	public void addDepartmentEvent(String name) {
		this.departmentList.getItems().add(name);
		if (!(departmentList.getItems().isEmpty())) {
			this.departmentList.setValue(this.departmentList.getItems().get(0));
		}

	}

	public VBox getSp() {
		return sp;
	}

	public void setRdButtonSelected() {
		this.rd[0].setSelected(true);
		this.rd[2].setSelected(true);
		this.rd[4].setSelected(true);
		this.rdForWorkPreference[2].setSelected(true);
		this.rdForSalary[0].setSelected(true);

	}

	private void exceptionAlert(Exception e) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
		alert.setTitle("Exception alert");
		alert.setHeaderText("exception :");
		alert.setContentText(e.getMessage());
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(logo);
		alert.showAndWait();
	}

	private void loadSucssesAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStyleClass().remove("alert");
		dialogPane.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
		alert.setTitle("Goodbye");
		alert.setTitle("Sucsses");
		alert.setHeaderText("It works !");
		alert.setContentText("going back to menu");

		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(logo);
		alert.showAndWait();
	}

	private void quitApp(Stage s) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStyleClass().remove("alert");
		dialogPane.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
		alert.setTitle("Goodbye");
		alert.setHeaderText("thank you for using our app");
		alert.setContentText("Simulation Systems ©");
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(logo);
		alert.showAndWait();
		s.close();
	}

	@Override
	public void addEmployeeHourlyToModel() {
		for (UIEventListener element : this.listeners) {
		}
	}

	public PreferenceType choosePrefrenceNav() {
		if (rdForWorkPreference[0].isSelected()) {
			return PreferenceType.EARLY_START;

		}
		if (rdForWorkPreference[1].isSelected()) {
			return PreferenceType.LATE_START;
		}
		if (rdForWorkPreference[2].isSelected()) {
			return PreferenceType.HOME;
		}
		if (rdForWorkPreference[3].isSelected()) {
			return PreferenceType.REGULAR_START;
		} else {
			return null;
		}

	}

	public Button getCompanyButton() {
		return companyButton;
	}

	@Override
	public void getObjects() {
		for (UIEventListener element : this.listeners) {
			this.employeeList.getItems().addAll(element.getEmployeesNames());
			this.roleList.getItems().addAll(element.getRolesNames());
			this.departmentList.getItems().addAll(element.getDeparmentsNames());

		}
	}

	public void showResutls() {
		this.toStringForEach.setVisible(false);
		HBox hb = new HBox();
		Label l1 = new Label("Choose which object result you wish to see");
		HBox hb1 = new HBox(l1);
		hb1.setAlignment(Pos.CENTER);
		BorderPane bp = new BorderPane();
		this.sp.getChildren().clear();
		Button[] btForShow = { new Button("Employee"), new Button("Roles"), new Button("Department"), new Button() };
		btForShow[3] = companyButton;
		for (Button element : btForShow) {
			element.setMaxWidth(Double.MAX_VALUE);
			hb.getChildren().add(element);
		}
		hb.setSpacing(20);
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(20, 30, 40, 60));
		this.sp.getChildren().addAll(hb1, hb, bp);
		btForShow[0].setOnAction(e -> {
			this.employeeList.setDisable(false);
			bp.setCenter(this.employeeList);
		});
		btForShow[1].setOnAction(e -> {
			this.roleList.setDisable(false);
			bp.setCenter(this.roleList);
		});
		btForShow[2].setOnAction(e -> {
			this.departmentList.setDisable(false);
			bp.setCenter(this.departmentList);
		});
		this.sp.setAlignment(Pos.TOP_CENTER);
		Text text = new Text();
		this.toStringForEach.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.toStringForEach.setPadding(new Insets(20, 30, 40, 50));
		this.sp.getChildren().add(toStringForEach);
		int size = sp.getChildren().size();
		this.sp.getChildren().get(size - 1).setVisible(false);
		this.departmentList.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				try {

					sp.getChildren().get(size - 1).setVisible(true);
					String st = listener.getObjectResult(objectType.DEPARTMENT, departmentList.getValue());
					text.setText(st);
					toStringForEach.setContent(text);
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}
		});

		this.roleList.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				try {
					sp.getChildren().get(size - 1).setVisible(true);
					String st = listener.getObjectToString(objectType.ROLE, roleList.getValue());
					text.setText(st);
					toStringForEach.setContent(text);
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}

		});
		this.employeeList.setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				try {
					this.sp.getChildren().get(size - 1).setVisible(true);
					String st = listener.getObjectResult(objectType.EMPLOYEE, employeeList.getValue());
					text.setText(st);
					this.toStringForEach.setContent(text);
				} catch (Exception e1) {
					this.exceptionAlert(e1);
				}
			}
		});
		btForShow[3].setOnAction(e -> {
			for (UIEventListener listener : listeners) {
				this.sp.getChildren().get(size - 1).setVisible(true);
				String st = listener.getCompanyResult();
				text.setText(st);
				this.toStringForEach.setContent(text);
			}
		});
		this.sp.setSpacing(10);
		this.changePane.setCenter(sp);
	}

	public void ChooseP() {
		if (rdForWorkPreference[2].isSelected() || rdForWorkPreference[3].isSelected()) {
			hourChange = 0;
		} else {
			if (getC1().getItems().isEmpty()) {
				hourChange = 0;
			} else {
				hourChange = getC1().getValue();
			}
		}

		if (getRd()[0].isSelected()) {
			chooseP = true;
		}
		if (getRd()[1].isSelected()) {
			chooseP = false;
		}

		if (getRd()[2].isSelected()) {
			syncd = true;
		}
		if (getRd()[3].isSelected()) {
			syncd = false;
		}

		if (getRd()[4].isSelected()) {
			workHome = true;
		}

		if (getRd()[5].isSelected()) {
			workHome = false;
		}

	}

}