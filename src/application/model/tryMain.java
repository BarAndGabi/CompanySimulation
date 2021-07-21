package application.model;

public class tryMain {

	public static void main(String[] args) {
		Company c = null;
		try {
			c = new Company();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c.getSimulationResults());
	}

}
