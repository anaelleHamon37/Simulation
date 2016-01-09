package simulation;

public abstract class Evenement {
	int date_simulation ;
	
	public int getDate_simulation() {
		return date_simulation;
	}

	public void setDate_simulation(int date_simulation) {
		this.date_simulation = date_simulation;
	}

	public abstract Simulateur doEvent(Simulateur simulateur);
	
}
