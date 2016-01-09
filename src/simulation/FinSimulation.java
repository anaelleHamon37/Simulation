package simulation;

public class FinSimulation extends Evenement{

	public FinSimulation(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		simulateur.dateSimu = date_simulation;
		simulateur.majAires();
		simulateur.genereResultats();
		simulateur.isFinished = true;
		return simulateur;
		
	}
	

}
