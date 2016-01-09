package simulation;

public class Main {

	
	public static void main(String[] args) {
		int n = 10, nt = 1, nt_max = 1;
		Simulateur simulateur = new Simulateur(n, nt, nt_max);
		simulateur.insertNewEvent(new DebutSimulation(0));
		while(!simulateur.isFinished())
			simulateur = simulateur.doNextEvent();
		simulateur.afficheResultats();
	}

}
