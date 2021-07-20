package application.model;

public class Simulation {
	private String companyDataAtSimulation;
	private int simulationNum;
	private static int Counter = 1;
	private double profit;

	public Simulation(String companyDataAtSimulation, double profit) {
		this.companyDataAtSimulation = companyDataAtSimulation;
		this.simulationNum = Counter++;
		this.profit = profit;
	}
}
