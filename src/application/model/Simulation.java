package application.model;

import java.io.Serializable;

public class Simulation implements Serializable {
	private int simulationNum;
	private static int Counter = 1;
	private String results;

	public Simulation(String r) {
		this.simulationNum = Counter++;
		this.results = r;
	}

	public String toString() {
		return "----------" + (this.simulationNum) + "----------\n" + this.results;
	}
}
