package simulation;

public class DebutSimulation extends Evenement{

	public DebutSimulation(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		System.out.println(date_simulation+" : Début de la simulation");
		simulateur.dateSimu = date_simulation;
		simulateur.initSimulateur();
		simulateur.insertNewEvent(new FinSimulation(14400));
		int nbCourrielsNuit = Probabilites.uniforme(20,80);
		for(int i=0; i<nbCourrielsNuit; i++)
			simulateur.insertNewEvent(new ArriveeCourriel(date_simulation));
		simulateur.insertNewEvent(new ArriveeCourriel(date_simulation + Probabilites.exponentielle(30)));
		simulateur.insertNewEvent(new ArriveeAppel(date_simulation + Probabilites.exponentielle(300)));
		simulateur.majAires();
		simulateur.last_dateSimu = simulateur.dateSimu;
		return simulateur;
	}
	

}
